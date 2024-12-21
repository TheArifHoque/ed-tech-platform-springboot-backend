package com.arifhoque.commonmodule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomHttpRequest {
    private String requestId;
    private HttpMethod methodType;
    private String url;
    private Map<String, String> headerParameterMap;
    private Map<String, String> queryParameterMap;
    private Map<String, Object> bodyParameterMap;
}
