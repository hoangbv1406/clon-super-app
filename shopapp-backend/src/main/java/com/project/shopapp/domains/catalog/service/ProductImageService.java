package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.response.ProductImageResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProductImageService {
    List<ProductImageResponse> getImagesByProductId(Integer productId);

    // Tích hợp lưu file trực tiếp
    List<ProductImageResponse> uploadImages(Integer currentUserId, Integer productId, List<MultipartFile> files);

    void deleteImage(Integer currentUserId, Integer imageId);
}