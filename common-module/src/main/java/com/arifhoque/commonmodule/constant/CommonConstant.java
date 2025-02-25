package com.arifhoque.commonmodule.constant;

public class CommonConstant {

    /**
     * General constants
     */
    public static final String REQUEST_ID = "Request-Id";
    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String ACCEPT_HEADER_KEY = "Accept";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String MESSAGE = "message";

    /**
     * Service specific constants
     */
    public static final String CONTENT_DELIVERY_API_BASE_URL = "content-delivery-service";
    public static final String USER_API_BASE_URL = "user-service/user";
    public static final String COURSE_API_BASE_URL = "course-management-service/course";
    public static final String COURSE_CONTENT_API_BASE_URL = "course-management-service/course-content";
    public static final String COURSE_PAYMENT_API_BASE_URL = "course-management-service/payment";
    public static final String COURSE_ENROLLMENT_API_BASE_URL = "course-management-service/enrollment";

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private CommonConstant() {
    }
}
