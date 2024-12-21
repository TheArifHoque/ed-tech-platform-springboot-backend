package com.arifhoque.apigateway.service;

import com.arifhoque.commonmodule.model.CustomHttpRequest;
import com.arifhoque.commonmodule.util.HttpCallLogic;
import com.arifhoque.commonmodule.util.RequestBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;
import static com.arifhoque.commonmodule.constant.CommonConstant.COURSE_ENROLLMENT_API_BASE_URL;

@Service
public class CourseEnrollmentAPIService {

    private final HttpCallLogic httpCallLogic;

    public CourseEnrollmentAPIService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    public Map<String, Object> enrollToCourse(Map<String, Object> courseEnrollmentInfoMap, String accessToken) {
        Map<String, String> headerParameterMap = Map.of(AUTHORIZATION_HEADER, accessToken);
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.POST,
                COURSE_ENROLLMENT_API_BASE_URL, headerParameterMap, null, courseEnrollmentInfoMap);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }

    public Map<String, Object> getAllEnrolledCourseIdsWithStatus(UUID userId, String accessToken) {
        String url = COURSE_ENROLLMENT_API_BASE_URL + "/courses";
        Map<String, String> headerParameterMap = Map.of(AUTHORIZATION_HEADER, accessToken);
        Map<String, String> queryParameterMap = Map.of("userId", userId.toString());
        CustomHttpRequest customHttpRequest = RequestBuilder.buildRequest(HttpMethod.GET, url, headerParameterMap,
                queryParameterMap, null);
        return httpCallLogic.getHttpResponseWithException(customHttpRequest);
    }
}
