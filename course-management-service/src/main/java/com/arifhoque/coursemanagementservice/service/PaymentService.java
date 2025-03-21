package com.arifhoque.coursemanagementservice.service;

import com.arifhoque.coursemanagementservice.model.PaymentInfo;
import com.arifhoque.coursemanagementservice.repository.PaymentInfoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_LIMIT = 50;

    private final PaymentInfoRepository paymentInfoRepository;

    public PaymentService(PaymentInfoRepository paymentInfoRepository) {
        this.paymentInfoRepository = paymentInfoRepository;
    }

    public List<PaymentInfo> getAllPaymentInfo(Integer pageNumber, Integer limit) {
        pageNumber = Optional.ofNullable(pageNumber).orElse(DEFAULT_PAGE_NUMBER);
        limit = Optional.ofNullable(limit).orElse(DEFAULT_LIMIT);
        PageRequest pageRequest = PageRequest.of(pageNumber, limit, Sort.by("date").descending());
        return paymentInfoRepository.findAllBy(pageRequest);
    }

    public void savePaymentInfo(PaymentInfo paymentInfo) throws Exception {
        PaymentInfo existingPaymentInfo = paymentInfoRepository.findByTrxId(paymentInfo.getTrxId());
        if (existingPaymentInfo != null) {
            throw new Exception("Payment with transaction id - " + paymentInfo.getTrxId() + " already exists!");
        }
        paymentInfoRepository.save(paymentInfo);
    }

    public void updatePaymentStatus(String trxId, String status) throws Exception {
        PaymentInfo existingPaymentInfo = paymentInfoRepository.findByTrxId(trxId);
        if (existingPaymentInfo == null) {
            throw new Exception("Payment with transaction id - " + trxId + " doesn't exists!");
        }
        existingPaymentInfo.setStatus(status);
        paymentInfoRepository.save(existingPaymentInfo);
    }
}
