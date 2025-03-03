package com.arifhoque.apigateway.service;

import com.arifhoque.commonmodule.model.CustomHttpRequest;
import com.arifhoque.commonmodule.util.HttpCallLogic;
import com.arifhoque.commonmodule.util.RequestBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;
import static com.arifhoque.commonmodule.constant.CommonConstant.COURSE_API_BASE_URL;

@Service
public class CourseAPIService {

    private final HttpCallLogic httpCallLogic;

    public CourseAPIService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    public Map<String, Object> getCourseById(UUID courseId) {
        String url = COURSE_API_BASE_URL + "/" + courseId.toString();
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.GET, url, null, null, null);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }

    public Map<String, Object> getAllCourses(Integer pageNumber, Integer limit) {
        Map<String, String> queryParameterMap = new HashMap<>();
        if (pageNumber != null) {
            queryParameterMap.put("pageNumber", pageNumber.toString());
        }
        if (limit != null) {
            queryParameterMap.put("limit", limit.toString());
        }
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.GET, COURSE_API_BASE_URL,
                null, queryParameterMap, null);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }

    public Map<String, Object> addNewCourse(Map<String, Object> course, String accessToken) {
        Map<String, String> headerParameterMap = Map.of(AUTHORIZATION_HEADER, accessToken);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.POST, COURSE_API_BASE_URL,
                headerParameterMap, null, course);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }

    public Map<String, Object> updateCourse(Map<String, Object> course, String accessToken) {
        String url = COURSE_API_BASE_URL + "/update";
        Map<String, String> headerParameterMap = Map.of(AUTHORIZATION_HEADER, accessToken);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.POST, url, headerParameterMap,
                null, course);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }

    public Map<String, Object> getAllEnrolledCourses(UUID userId, String accessToken) {
        String url = COURSE_API_BASE_URL + "/enrollment/" + userId.toString();
        Map<String, String> headerParameterMap = Map.of(AUTHORIZATION_HEADER, accessToken);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.GET, url, headerParameterMap,
                null, null);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }

    public Map<String, Object> getEnrollmentStatus(UUID courseId, UUID userId, String accessToken) {
        String url = COURSE_API_BASE_URL + "/enrollment/" + courseId.toString() + "/" + userId.toString();
        Map<String, String> headerParameterMap = Map.of(AUTHORIZATION_HEADER, accessToken);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.GET, url, headerParameterMap,
                null, null);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }
}
