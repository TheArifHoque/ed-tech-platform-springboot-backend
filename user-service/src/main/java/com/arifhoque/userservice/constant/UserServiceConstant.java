package com.arifhoque.userservice.constant;

/**
 * Utility class containing the common constants of the user-service
 *
 * @author Ariful Hoque
 */
public class UserServiceConstant {

    /**
     * Constant for standard user role.
     * This role permits access to user resources and actions within the application
     */
    public static final String ROLE_USER = "USER";

    /**
     * Constant for standard user role.
     * This role grants access to administrative resource and actions within the application
     */
    public static final String ROLE_ADMIN = "ADMIN";

    /**
     * Regular expression for validating email address
     * The pattern enforces basic structure for email address with proper domain extension
     */
    public static final String VALID_EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.a-zA-Z0-9_+&*-]+)" +
            "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    /**
     * Regular expression for validating password
     * At least one uppercase, one lowercase, one digit and one special character
     * with the minimum length of 8 characters
     */
    public static final String VALID_PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    /**
     * Private constructor to prevent instantiation of this class
     */
    private UserServiceConstant() {
    }
}
