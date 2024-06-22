package com.arifhoque.coursemanagementservice.controller;

import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import com.arifhoque.coursemanagementservice.model.PaymentInfo;
import com.arifhoque.coursemanagementservice.service.PaymentService;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomHttpResponse> savePaymentInfo(@RequestBody PaymentInfo paymentInfo) {
        try {
            paymentService.savePaymentInfo(paymentInfo);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to add payment info: Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED,
                Map.of("message", "Successfully added payment info"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomHttpResponse> getAllPaymentInfo(@RequestParam @Nullable Integer pageNumber,
                                                                @RequestParam @Nullable Integer limit) {
        List<PaymentInfo> paymentInfoList;
        try {
            paymentInfoList = paymentService.getAllPaymentInfo(pageNumber, limit);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to retrieve all payment info: Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("paymentInfoList", paymentInfoList));
    }

    @PostMapping("/approval")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomHttpResponse> updatePaymentStatus(@RequestBody Map<String, String> paymentStatusMap) {
        try {
            paymentService.updatePaymentStatus(paymentStatusMap.get("trxId"), paymentStatusMap.get("status"));
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.EXPECTATION_FAILED, "417",
                    "Failed to update payment status: Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK,
                Map.of("message", "Successfully updated payment status"));
    }
}
