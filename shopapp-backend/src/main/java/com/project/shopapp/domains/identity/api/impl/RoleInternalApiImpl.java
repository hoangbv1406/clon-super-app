package com.project.shopapp.domains.identity.api.impl; // Cậu nhớ sửa package cho khớp với cấu trúc thư mục nhé

import com.project.shopapp.domains.identity.api.RoleInternalApi;
import org.springframework.stereotype.Service;

@Service // Rất quan trọng: Phải có tem này thì Spring Boot mới bế nó đi inject được
public class RoleInternalApiImpl implements RoleInternalApi {

    @Override
    public Integer getDefaultRoleId(String roleName) {
        // TODO: Sau này cậu tiêm RoleRepository vào đây để query DB và gắn @Cacheable nhé.
        // Dựa vào file SQL hôm trước của cậu, tớ return cứng các ID tạm thời để app chạy qua mặt đã:
        // (1, 'user'), (2, 'admin'), (3, 'vendor'), (4, 'staff')

        if ("admin".equalsIgnoreCase(roleName)) return 2;
        if ("vendor".equalsIgnoreCase(roleName)) return 3;
        if ("staff".equalsIgnoreCase(roleName)) return 4;

        return 1; // Mặc định trả về 1 (role: user)
    }
}