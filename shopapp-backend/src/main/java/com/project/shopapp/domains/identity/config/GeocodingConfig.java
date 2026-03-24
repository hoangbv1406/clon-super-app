package com.project.shopapp.domains.identity.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GeocodingConfig {
    // TODO: Cấu hình Bean GoogleMapsApiClient hoặc MapboxClient tại đây.
    // Client này sẽ được gọi Asynchronous trong UserAddressServiceImpl để tự fill lat/lng nếu request rỗng.
}