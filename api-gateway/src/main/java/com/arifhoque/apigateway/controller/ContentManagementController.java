package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.ContentAPIService;
import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.arifhoque.commonmodule.constant.CommonConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/content-management-api")
public class ContentManagementController {

    private final ContentAPIService contentAPIService;

    public ContentManagementController(ContentAPIService contentAPIService) {
        this.contentAPIService = contentAPIService;
    }

    @GetMapping("/file-system-storage/{contentUrl}")
    public ResponseEntity<?> getContent(@PathVariable String contentUrl) {
        try {
            return contentAPIService.serveContent(contentUrl);
        } catch (Exception ex) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.NOT_FOUND, "404",
                    "Failed to fetch the content! Reason: " + ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<CustomHttpResponse> saveContents(@RequestHeader(AUTHORIZATION_HEADER) String accessToken,
                                                           @RequestParam MultipartFile[] contents) {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, contentAPIService.saveContents(contents,
                accessToken));
    }
}
