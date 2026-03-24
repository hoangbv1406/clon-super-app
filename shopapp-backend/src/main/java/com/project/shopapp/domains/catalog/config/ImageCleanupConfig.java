package com.project.shopapp.domains.catalog.config;

import com.project.shopapp.domains.catalog.event.ProductImageDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageCleanupConfig {

    // private final StorageService storageService;

    @Async // Phải có @Async để không làm chậm request Delete của Admin
    @EventListener
    public void handleImageDeletedEvent(ProductImageDeletedEvent event) {
        log.info("Received event to delete physical file from S3: {}", event.getImageUrl());
        try {
            // storageService.deleteFile(event.getImageUrl());
            log.info("Successfully deleted file from S3.");
        } catch (Exception e) {
            log.error("Failed to delete physical file from S3: {}", e.getMessage());
            // Có thể Retry bằng Delay Queue nếu cần
        }
    }
}