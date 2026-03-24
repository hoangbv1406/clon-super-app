package com.project.shopapp.domains.community.config;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewBadWordFilterConfig {
    // TODO: Tích hợp thư viện DFA (Deterministic Finite Automaton)
    // hoặc gọi API AI (như Perspective API) để lọc các từ ngữ chửi thề,
    // link spam đối thủ trước khi gán status = APPROVED.
    // Nếu phát hiện vi phạm, gán status = REJECTED ngay lập tức.
}