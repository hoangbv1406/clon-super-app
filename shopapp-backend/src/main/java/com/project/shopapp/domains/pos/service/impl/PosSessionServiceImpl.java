package com.project.shopapp.domains.pos.service.impl;

import com.project.shopapp.domains.pos.dto.request.PosSessionCloseRequest;
import com.project.shopapp.domains.pos.dto.request.PosSessionOpenRequest;
import com.project.shopapp.domains.pos.dto.response.PosSessionResponse;
import com.project.shopapp.domains.pos.entity.PosSession;
import com.project.shopapp.domains.pos.enums.PosSessionStatus;
import com.project.shopapp.domains.pos.event.PosSessionClosedEvent;
import com.project.shopapp.domains.pos.mapper.PosSessionMapper;
import com.project.shopapp.domains.pos.repository.PosSessionRepository;
import com.project.shopapp.domains.pos.service.PosSessionService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PosSessionServiceImpl implements PosSessionService {

    private final PosSessionRepository sessionRepository;
    private final PosSessionMapper sessionMapper;
    private final ApplicationEventPublisher eventPublisher;

    // Giả lập Facade gọi sang Sales Module để lấy Tổng tiền mặt đã thu trong ca này
    // private final SalesInternalApi salesApi;

    @Override
    @Transactional
    public PosSessionResponse openSession(Integer userId, Integer shopId, PosSessionOpenRequest request) {
        if (sessionRepository.existsByShopIdAndUserIdAndStatus(shopId, userId, PosSessionStatus.OPEN)) {
            throw new ConflictException("Bạn đang có một ca làm việc chưa đóng. Hãy đóng ca cũ trước.");
        }

        PosSession session = PosSession.builder()
                .shopId(shopId)
                .userId(userId)
                .startAt(LocalDateTime.now())
                .openingCash(request.getOpeningCash())
                .note(request.getNote())
                .status(PosSessionStatus.OPEN)
                .build();

        session.setCreatedBy(userId);
        return sessionMapper.toDto(sessionRepository.save(session));
    }

    @Override
    @Transactional
    public PosSessionResponse closeSession(Integer userId, Integer shopId, PosSessionCloseRequest request) {
        PosSession session = sessionRepository.findByShopIdAndUserIdAndStatus(shopId, userId, PosSessionStatus.OPEN)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy ca làm việc đang mở"));

        // 1. Tính toán Expected Cash (MOCK LOGIC)
        // BigDecimal cashSales = salesApi.getTotalCashRevenueBySessionId(session.getId());
        BigDecimal cashSales = new BigDecimal("5000000"); // Giả sử thu được 5tr

        BigDecimal expectedCash = session.getOpeningCash().add(cashSales);
        BigDecimal difference = request.getClosingCash().subtract(expectedCash); // Thực tế trừ Lý thuyết

        // Bắt buộc nhập Note nếu lệch tiền
        if (difference.compareTo(BigDecimal.ZERO) != 0 && (request.getNote() == null || request.getNote().isBlank())) {
            throw new InvalidParamException("Bắt buộc phải nhập ghi chú giải trình khi số tiền đếm được bị lệch.");
        }

        session.setClosingCash(request.getClosingCash());
        session.setExpectedCash(expectedCash);
        session.setDifferenceCash(difference);
        session.setEndAt(LocalDateTime.now());
        session.setStatus(PosSessionStatus.CLOSED);
        if (request.getNote() != null) {
            session.setNote(session.getNote() + " | Đóng ca: " + request.getNote());
        }
        session.setUpdatedBy(userId);

        PosSession saved = sessionRepository.save(session);

        // Bắn Event kiểm toán
        eventPublisher.publishEvent(new PosSessionClosedEvent(
                saved.getId(), saved.getShopId(), saved.getUserId(), saved.getDifferenceCash()
        ));

        return sessionMapper.toDto(saved);
    }
}