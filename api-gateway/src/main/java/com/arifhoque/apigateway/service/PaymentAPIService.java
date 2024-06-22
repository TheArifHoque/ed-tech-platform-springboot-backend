package com.arifhoque.apigateway.service;

import com.arifhoque.commonmodule.model.CustomHttpRequest;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.HttpCallLogic;
import com.arifhoque.commonmodule.util.RequestBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;

@Service
public class PaymentAPIService {

    private final HttpCallLogic httpCallLogic;

    public PaymentAPIService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    public List<Map<String, Object>> getAllPaymentInfo(Integer pageNumber, Integer limit,
                                                       String accessToken) throws Exception {
        String url = "course-management-service/payment";
        Map<String, String> headerParameterMap = new HashMap<>();
        headerParameterMap.put(AUTHORIZATION_HEADER, accessToken);
        Map<String, String> urlParameterMap = new HashMap<>();
        if (pageNumber != null) {
            urlParameterMap.put("pageNumber", pageNumber.toString());
        }
        if (limit != null) {
            urlParameterMap.put("limit", limit.toString());
        }
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.GET, url,
                headerParameterMap, urlParameterMap, null);
        try {
            ResponseEntity<CustomHttpResponse> responseEntity = httpCallLogic.executeRequest(customHttpRequest);
            return (List<Map<String, Object>>) responseEntity.getBody().getResponseBody().get("paymentInfoList");
        } catch (Exception e) {
            throw new Exception("Error occurred while calling COURSE-MANAGEMENT-SERVICE!");
        }
    }

    public String savePaymentInfo(Map<String, Object> paymentInfo, String accessToken) throws Exception {
        String url = "course-management-service/payment";
        Map<String, String> headerParameterMap = new HashMap<>();
        headerParameterMap.put(AUTHORIZATION_HEADER, accessToken);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.POST, url,
                headerParameterMap, null, paymentInfo);
        try {
            ResponseEntity<CustomHttpResponse> responseEntity = httpCallLogic.executeRequest(customHttpRequest);
            return (String) responseEntity.getBody().getResponseBody().get("message");
        } catch (Exception e) {
            throw new Exception("Error occurred while calling COURSE-MANAGEMENT-SERVICE!");
        }
    }

    public String updatePaymentInfo(String trxId, String status, String accessToken) throws Exception {
        String url = "course-management-service/payment/approval";
        Map<String, String> headerParameterMap = new HashMap<>();
        headerParameterMap.put(AUTHORIZATION_HEADER, accessToken);
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("trxId", trxId);
        bodyMap.put("status", status);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.POST, url,
                headerParameterMap, null, bodyMap);
        try {
            ResponseEntity<CustomHttpResponse> responseEntity = httpCallLogic.executeRequest(customHttpRequest);
            return (String) responseEntity.getBody().getResponseBody().get("message");
        }  catch (Exception e) {
            throw new Exception("Error occurred while calling COURSE-MANAGEMENT-SERVICE!");
        }
    }
}
