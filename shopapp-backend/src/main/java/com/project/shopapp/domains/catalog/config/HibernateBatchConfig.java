package com.project.shopapp.domains.catalog.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HibernateBatchConfig {

    // Cấu hình ép Hibernate gom các câu lệnh Insert/Update lại thành từng gói 50 rows
    // trước khi gửi qua mạng (Network) tới MySQL.
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return (Map<String, Object> properties) -> {
            properties.put("hibernate.jdbc.batch_size", "50");
            properties.put("hibernate.order_inserts", "true");
            properties.put("hibernate.order_updates", "true");
        };
    }
}