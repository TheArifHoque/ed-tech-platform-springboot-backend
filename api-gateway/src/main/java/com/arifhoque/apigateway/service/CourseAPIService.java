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

@Service
public class CourseAPIService {

    private final HttpCallLogic httpCallLogic;

    public CourseAPIService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    public List<Map<String, Object>> getAllCourses(Integer pageNumber, Integer limit) throws Exception {
        String url = "course-management-service/course";
        Map<String, String>  urlParameterMap = new HashMap<>();
        if (pageNumber != null) {
            urlParameterMap.put("pageNumber", pageNumber.toString());
        }
        if (limit != null) {
            urlParameterMap.put("limit", limit.toString());
        }
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.GET, url,
                null, urlParameterMap, null);
        try {
            ResponseEntity<CustomHttpResponse> responseEntity = httpCallLogic.executeRequest(customHttpRequest);
            return (List<Map<String, Object>>) responseEntity.getBody().getResponseBody().get("courseList");
        } catch (Exception e) {
            throw new Exception("Error occurred while calling COURSE-MANAGEMENT-SERVICE!");
        }
    }
}
