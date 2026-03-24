package com.project.shopapp.domains.pos.service;
import com.project.shopapp.domains.pos.dto.request.PosSessionCloseRequest;
import com.project.shopapp.domains.pos.dto.request.PosSessionOpenRequest;
import com.project.shopapp.domains.pos.dto.response.PosSessionResponse;

public interface PosSessionService {
    PosSessionResponse openSession(Integer userId, Integer shopId, PosSessionOpenRequest request);
    PosSessionResponse closeSession(Integer userId, Integer shopId, PosSessionCloseRequest request);
}