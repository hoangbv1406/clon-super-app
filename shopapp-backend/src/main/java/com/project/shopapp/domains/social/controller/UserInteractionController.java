package com.project.shopapp.domains.social.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.social.dto.request.InteractionLogRequest;
import com.project.shopapp.domains.social.service.UserInteractionService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics/interactions")
@RequiredArgsConstructor
public class UserInteractionController {

    private final UserInteractionService interactionService;
    private final SecurityUtils securityUtils;

    @PostMapping
    public ResponseEntity<ResponseObject<Void>> logInteraction(
            @Valid @RequestBody InteractionLogRequest request,
            HttpServletRequest httpServletRequest) {

        Integer userId = securityUtils.getLoggedInUserId(); // Cho phép null nếu Guest lướt App
        String ipAddress = httpServletRequest.getRemoteAddr();

        // Lệnh này chạy Async (Bất đồng bộ) nên API trả về HTTP 200 OK ngay lập tức
        interactionService.logInteraction(userId, ipAddress, request);

        return ResponseEntity.ok(ResponseObject.success(null, "Logged"));
    }
}