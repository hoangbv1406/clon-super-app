package com.project.shopapp.shared.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service // RẤT QUAN TRỌNG: Cái tem này giúp Spring Boot tìm thấy file này!
public class LocalStorageServiceImpl implements StorageService {

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        // Code lưu file thật cậu có thể viết sau.
        // Tạm thời cứ return ra 1 cái tên file để code chạy qua mặt đã.
        return "uploaded_" + System.currentTimeMillis() + ".jpg";
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
        // Code xóa file thật cậu viết sau
        System.out.println("Đã xóa file: " + fileName);
    }

    @Override
    public String getFileUrl(String fileName) {
        // Trả về URL thật cậu viết sau
        return "http://localhost:8088/uploads/" + fileName;
    }
}