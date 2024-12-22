package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.PaymentAPIService;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/payment-management-api")
public class PaymentManagementController {

    private final PaymentAPIService paymentAPIService;

    public PaymentManagementController(PaymentAPIService paymentAPIService) {
        this.paymentAPIService = paymentAPIService;
    }

    @GetMapping
    public ResponseEntity<CustomHttpResponse> getAllPaymentInfo(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                                @RequestParam @Nullable Integer pageNumber,
                                                                @RequestParam @Nullable Integer limit) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, paymentAPIService.getAllPaymentInfo(pageNumber,
                limit, accessToken));
    }

    @PostMapping
    public ResponseEntity<CustomHttpResponse> savePaymentInfo(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                              @RequestBody Map<String, Object> paymentInfo) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, paymentAPIService.savePaymentInfo(paymentInfo,
                accessToken));
    }

    @PostMapping("/approval")
    public ResponseEntity<CustomHttpResponse> approvePayment(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                             @RequestBody Map<String, String> paymentStatusMap) {
        String trxId = paymentStatusMap.get("trxId");
        String status = paymentStatusMap.get("status");
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, paymentAPIService.updatePaymentStatus(trxId, status,
                accessToken));
    }
}
