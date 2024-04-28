package com.arifhoque.commonmodule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomHttpRequest {
    private String requestId;
    private HttpMethod methodType;
    private String url;
    private Map<String, String> headerParameterMap = new HashMap<>();
    private Map<String, String> urlParameterMap = new HashMap<>();
    private Map<String, Object> bodyMap;
}
