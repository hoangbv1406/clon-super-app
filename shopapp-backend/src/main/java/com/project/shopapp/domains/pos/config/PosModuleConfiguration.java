package com.project.shopapp.domains.pos.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.project.shopapp.domains.pos")
public class PosModuleConfiguration {
    // TODO: Khởi tạo ReceiptPrinterClient / WebSocket Config cho máy POS nội bộ
}