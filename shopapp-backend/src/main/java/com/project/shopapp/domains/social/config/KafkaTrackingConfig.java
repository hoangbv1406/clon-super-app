package com.project.shopapp.domains.social.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTrackingConfig {
    // TODO: Khi lượng traffic đạt ngưỡng 10.000 request/s, việc Insert thẳng vào MySQL sẽ gây nghẽn rớt mạng.
    // Lúc đó, ta sẽ sửa UserInteractionController để đẩy cục JSON Request vào Apache Kafka.
    // Sau đó một Service khác (Viết bằng Python hoặc Go) sẽ hút từ Kafka nhét vào ClickHouse (OLAP DB chuyên cho Analytics).
}