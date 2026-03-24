package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.dto.request.ProductImageCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductImageResponse;
import com.project.shopapp.domains.catalog.entity.ProductImage;
import com.project.shopapp.domains.catalog.enums.ProductImageType;
import com.project.shopapp.domains.catalog.event.ProductImageDeletedEvent;
import com.project.shopapp.domains.catalog.mapper.ProductImageMapper;
import com.project.shopapp.domains.catalog.repository.ProductImageRepository;
import com.project.shopapp.domains.catalog.repository.ProductRepository;
import com.project.shopapp.domains.catalog.service.ProductImageService;
import com.project.shopapp.shared.application.StorageService; // Sử dụng StorageService cậu đã tạo ở shared/application
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ProductImageMapper imageMapper;
    private final StorageService storageService; // Gọi module S3 Upload
    private final ApplicationEventPublisher eventPublisher;

    private static final int MAX_IMAGES_PER_PRODUCT = 8;

    @Override
    @Transactional(readOnly = true)
    public List<ProductImageResponse> getImagesByProductId(Integer productId) {
        return imageRepository.findByProductIdAndIsDeletedOrderByDisplayOrderAsc(productId, 0L)
                .stream().map(imageMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProductImageResponse> uploadImages(Integer currentUserId, Integer productId, List<MultipartFile> files) {
        if (!productRepository.existsById(productId)) {
            throw new DataNotFoundException("Sản phẩm không tồn tại");
        }

        long currentImageCount = imageRepository.countByProductIdAndIsDeleted(productId, 0L);
        if (currentImageCount + files.size() > MAX_IMAGES_PER_PRODUCT) {
            throw new ConflictException("Mỗi sản phẩm chỉ được tải lên tối đa " + MAX_IMAGES_PER_PRODUCT + " ảnh.");
        }

        List<ProductImage> savedImages = new ArrayList<>();
        int currentOrder = (int) currentImageCount;

        for (MultipartFile file : files) {
            try {
                // 1. Lưu file lên S3/Local
                String uploadedFileName = storageService.storeFile(file);

                // 2. Lưu record vào DB
                ProductImageCreateRequest req = new ProductImageCreateRequest();
                req.setProductId(productId);
                req.setImageUrl(uploadedFileName); // Hoặc URL full tùy config CDN
                req.setDisplayOrder(++currentOrder);
                req.setImageType(ProductImageType.GALLERY.name());

                ProductImage image = imageMapper.toEntityFromRequest(req);
                image.setCreatedBy(currentUserId);
                savedImages.add(imageRepository.save(image));

            } catch (Exception e) {
                throw new InvalidParamException("Lỗi upload ảnh: " + e.getMessage());
            }
        }
        return savedImages.stream().map(imageMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteImage(Integer currentUserId, Integer imageId) {
        ProductImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new DataNotFoundException("Ảnh không tồn tại"));

        // Đánh dấu xóa mềm
        image.setIsDeleted(System.currentTimeMillis());
        image.setUpdatedBy(currentUserId);
        imageRepository.save(image);

        // BẮN EVENT ASYNC: Báo cho S3 Listener biết để gọi API xóa file vật lý trên AWS
        eventPublisher.publishEvent(new ProductImageDeletedEvent(image.getId(), image.getImageUrl()));
    }
}