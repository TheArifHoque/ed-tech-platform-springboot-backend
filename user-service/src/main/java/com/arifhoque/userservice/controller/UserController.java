package com.arifhoque.userservice.controller;

import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import com.arifhoque.userservice.model.User;
import com.arifhoque.userservice.service.KeycloakService;
import com.arifhoque.userservice.service.UserService;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.arifhoque.userservice.constant.UserServiceConstant.ROLE_ADMIN;
import static com.arifhoque.userservice.constant.UserServiceConstant.ROLE_USER;

/**
 * Main controller class for the application 'user-service'.
 * All the APIs related to user's personal information are written here.
 *
 * @author Ariful Hoque
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final KeycloakService keycloakService;
    private final UserService userService;

    /**
     * Constructor for UserController class.
     *
     * @param keycloakService instance of KeycloakService.
     * @param userService     instance of UserService.
     */
    public UserController(KeycloakService keycloakService, UserService userService) {
        this.keycloakService = keycloakService;
        this.userService = userService;
    }

    /**
     * API to add / register user with role 'USER'.
     * To use this API, client application doesn't need to pass any access token.
     *
     * @param user request payload containing user data.
     * @return success only if the user can be successfully added to both KeyCloak auth server
     * and 'user-service-db'. Else returns 400-Bad Request.
     */
    @PostMapping
    public ResponseEntity<CustomHttpResponse> addRegularUser(@RequestBody User user) {
        try {
            userService.validateEmail(user.getEmail());
            userService.validatePassword(user.getPassword());
            user.setRole(ROLE_USER);
            UUID userId = keycloakService.registerNewUser(user);
            user.setUserId(userId);
            userService.addUser(user);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to add user! Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED, Map.of("message", "Successfully added user"));
    }

    /**
     * API to add / register user with role 'ADMIN'.
     * To use this API, client application needs to pass access token with role 'ADMIN'.
     *
     * @param user request payload containing user's data.
     * @return success only if the user can be successfully added to both KeyCloak auth server
     * and 'user-service-db'. Else returns 400-Bad Request.
     */
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomHttpResponse> addAdminUser(@RequestBody User user) {
        try {
            userService.validateEmail(user.getEmail());
            userService.validatePassword(user.getPassword());
            user.setRole(ROLE_ADMIN);
            UUID userId = keycloakService.registerNewUser(user);
            user.setUserId(userId);
            userService.addUser(user);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to add user! Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.CREATED,
                Map.of("message", "Successfully added admin user"));
    }

    /**
     * API to fetch any particular user's information.
     * To use this API, client application needs to pass access token with either role 'ADMIN' or 'USER'.
     *
     * @param userId userId of the user.
     * @return user information found in the 'user-service-db'. Else returns 404-Not Found.
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CustomHttpResponse> getUserById(@PathVariable UUID userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.NOT_FOUND, "404",
                    "User not found with this user id!");
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("user", user));
    }

    /**
     * API to list all the user with role 'USER'.
     * To use this API, client application needs to pass access token with either role 'ADMIN' or 'USER'.
     *
     * @return list of user information found in the KeyCloak auth server with role 'USER' and in 'user-service-db'.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CustomHttpResponse> getAllRegularUsers() {
        List<UUID> userIds = keycloakService.getUserIdsByRole(ROLE_USER);
        List<User> userList = userService.getListOfUser(userIds);
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("userList", userList));
    }

    /**
     * API to list all the user with role 'ADMIN'.
     * To use this API, client application needs to pass access token with either role 'ADMIN' or 'USER'.
     *
     * @return list of user information found in the KeyCloak auth server with role 'ADMIN' and in 'user-service-db'.
     */
    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CustomHttpResponse> getAllAdminUsers() {
        List<UUID> userIds = keycloakService.getUserIdsByRole(ROLE_ADMIN);
        List<User> userList = userService.getListOfUser(userIds);
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("userList", userList));
    }

    /**
     * API to fetch a list of user info with role 'ADMIN' & 'USER'.
     * To use this API, client application needs to pass access token with either role 'ADMIN' or 'USER'.
     *
     * @param userIdsMap map containing a list of user ids whose information is to be queried.
     * @return list of user information found in the 'user-service-db'.
     */
    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CustomHttpResponse> getListOfUser(@RequestBody Map<String, List<UUID>> userIdsMap) {
        List<User> userList;
        try {
            userList = userService.getListOfUser(userIdsMap.get("userIds"));
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "400",
                    "Failed to fetch user information! Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("userList", userList));
    }

    /**
     * API to update user profile information.
     * To use this API, client application needs to pass access token of that particular user.
     * Or it needs to pass access token with role 'ADMIN'.
     *
     * @param user request payload containing user profile information.
     * @return success if operation is successful. Else returns 417-Expectation Failed.
     */
    @PostMapping("/profile")
    @PreAuthorize("hasRole('ADMIN') or #user.userId.toString() == authentication.principal.subject")
    public ResponseEntity<CustomHttpResponse> updateProfile(@RequestBody User user) {
        try {
            keycloakService.updateUser(user);
            userService.updateUser(user);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.EXPECTATION_FAILED, "417",
                    "Failed to update user information! Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("message",
                "Successfully updated user"));
    }

    /**
     * API to update user profile picture.
     * To use this API, client application needs to pass access token of that particular user.
     * Or it needs to pass access token with role 'ADMIN'.
     *
     * @param imageUrlMap map containing user id and image url data.
     * @return success if operation is successful. Else returns 417-Expectation Failed.
     */
    @PostMapping("/image")
    @PreAuthorize("hasRole('ADMIN') or #imageUrlMap.get('userId') == authentication.principal.subject")
    public ResponseEntity<CustomHttpResponse> updatePhoto(@RequestBody Map<String, String> imageUrlMap) {
        try {
            userService.updateProfileImage(UUID.fromString(imageUrlMap.get("userId")), imageUrlMap.get("imageUrl"));
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.EXPECTATION_FAILED, "417",
                    "Failed to update user profile image! Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("message",
                "Successfully updated profile image"));
    }

    /**
     * API to update account password.
     * To use this API, client application needs to pass access token of that particular user.
     * Or it needs to pass access token with role 'ADMIN'.
     *
     * @param passwordMap map containing user id and new password.
     * @return success if operation is successful. Else returns 417-Expectation Failed.
     */
    @PostMapping("/password")
    @PreAuthorize("hasRole('ADMIN') or #passwordMap.get('userId') == authentication.principal.subject")
    public ResponseEntity<CustomHttpResponse> updatePassword(@RequestBody Map<String, String> passwordMap) {
        try {
            String userId = passwordMap.get("userId");
            String password = passwordMap.get("password");
            userService.validatePassword(password);
            UserResource userResource = keycloakService.getUserResourceById(userId);
            keycloakService.updateUserCredentials(userResource, password);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.EXPECTATION_FAILED, "417",
                    "Failed to update user password! Reason: " + e.getMessage());
        }
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, Map.of("message",
                "Successfully updated user password"));
    }
}
