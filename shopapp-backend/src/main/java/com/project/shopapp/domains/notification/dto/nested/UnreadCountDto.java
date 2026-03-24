// --- nested/UnreadCountDto.java ---
package com.project.shopapp.domains.notification.dto.nested;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnreadCountDto {
    private long unreadCount; // Trả về số badge đỏ trên icon cái chuông
}