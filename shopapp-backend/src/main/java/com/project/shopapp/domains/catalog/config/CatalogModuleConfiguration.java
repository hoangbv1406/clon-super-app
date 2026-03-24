package com.project.shopapp.domains.catalog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Cấu hình cốt lõi cho Module Catalog.
 * Module này chứa Categories, Brands, Products, Variants và Specs.
 */
@Configuration
@ComponentScan(basePackages = "com.project.shopapp.domains.catalog")
public class CatalogModuleConfiguration {
    // TODO: Setup Beans cho Elasticsearch (nếu dự án áp dụng fulltext search sau này)
}