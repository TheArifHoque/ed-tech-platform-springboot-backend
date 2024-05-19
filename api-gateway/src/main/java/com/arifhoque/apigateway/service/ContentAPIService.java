package com.arifhoque.apigateway.service;

import com.arifhoque.commonmodule.model.CustomHttpRequest;
import com.arifhoque.commonmodule.util.HttpCallLogic;
import com.arifhoque.commonmodule.util.RequestBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.arifhoque.commonmodule.constant.CommonConstant.ACCEPT_HEADER_KEY;

@Service
public class ContentAPIService {

    private final HttpCallLogic httpCallLogic;

    public ContentAPIService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    public ResponseEntity<byte[]> serveContent(String contentUrl) throws Exception {
        String url = "content-delivery-service/file-system-storage/" + contentUrl;
        Map<String, String> headerParameterMap = new HashMap<>();
        headerParameterMap.put(ACCEPT_HEADER_KEY, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.GET,
                url, headerParameterMap, null, null);
        try {
            return httpCallLogic.fetchMediaContent(customHttpRequest);
        } catch (Exception e) {
            throw new Exception("Error occurred while calling CONTENT-DELIVERY-SERVICE!");
        }
    }
}
