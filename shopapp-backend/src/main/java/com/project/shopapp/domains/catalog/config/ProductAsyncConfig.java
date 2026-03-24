package com.project.shopapp.domains.catalog.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductAsyncConfig {
    // TODO: Bean quản lý ThreadPool riêng cho các tác vụ đồng bộ hình ảnh Product và sync ElasticSearch.
}