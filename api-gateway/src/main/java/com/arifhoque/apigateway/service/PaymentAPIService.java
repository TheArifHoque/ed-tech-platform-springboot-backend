package com.arifhoque.apigateway.service;

import com.arifhoque.commonmodule.model.CustomHttpRequest;
import com.arifhoque.commonmodule.util.HttpCallLogic;
import com.arifhoque.commonmodule.util.RequestBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;
import static com.arifhoque.commonmodule.constant.CommonConstant.COURSE_PAYMENT_API_BASE_URL;

@Service
public class PaymentAPIService {

    private final HttpCallLogic httpCallLogic;

    public PaymentAPIService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    public Map<String, Object> getAllPaymentInfo(Integer pageNumber, Integer limit, String accessToken) {
        Map<String, String> headerParameterMap = Map.of(AUTHORIZATION_HEADER, accessToken);
        Map<String, String> queryParameterMap = new HashMap<>();
        if (pageNumber != null) {
            queryParameterMap.put("pageNumber", pageNumber.toString());
        }
        if (limit != null) {
            queryParameterMap.put("limit", limit.toString());
        }
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.GET, COURSE_PAYMENT_API_BASE_URL,
                headerParameterMap, queryParameterMap, null);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }

    public Map<String, Object> savePaymentInfo(Map<String, Object> paymentInfo, String accessToken) {
        Map<String, String> headerParameterMap = Map.of(AUTHORIZATION_HEADER, accessToken);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.POST, COURSE_PAYMENT_API_BASE_URL,
                headerParameterMap, null, paymentInfo);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }

    public Map<String, Object> updatePaymentStatus(Map<String, Object> paymentInfo, String accessToken) {
        String url = COURSE_PAYMENT_API_BASE_URL + "/approval";
        Map<String, String> headerParameterMap = Map.of(AUTHORIZATION_HEADER, accessToken);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.POST, url, headerParameterMap,
                null, paymentInfo);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }
}
