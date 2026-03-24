// --- controller/WebhookController.java ---
package com.project.shopapp.domains.finance.controller;

import com.project.shopapp.domains.finance.dto.request.WebhookIPNRequest;
import com.project.shopapp.domains.finance.service.FinanceTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final FinanceTransactionService transactionService;

    // PUBLIC: VNPAY / Momo sẽ dội request vào đây
    @GetMapping("/vnpay/ipn")
    public ResponseEntity<String> vnpayIpnHandler(@RequestParam Map<String, Object> allRequestParams) {
        WebhookIPNRequest req = new WebhookIPNRequest();
        req.setPayload(allRequestParams);

        // Trả về String JSON thuần túy theo chuẩn tài liệu của VNPAY
        String response = transactionService.handleVnpayWebhook(req);
        return ResponseEntity.ok(response);
    }
}
