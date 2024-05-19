package com.arifhoque.apigateway.controller;

import com.arifhoque.apigateway.service.ContentAPIService;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content-api")
public class ContentDeliveryController {

    private final ContentAPIService contentAPIService;

    public ContentDeliveryController(ContentAPIService contentAPIService) {
        this.contentAPIService = contentAPIService;
    }

    @GetMapping("/file-system-storage/{contentUrl}")
    public ResponseEntity<?> getContent(@PathVariable String contentUrl) {
        try {
            return contentAPIService.serveContent(contentUrl);
        } catch (Exception e) {
            return ResponseBuilder.buildFailureResponse(HttpStatus.NOT_FOUND, "404",
                    "Failed to fetch the content! Reason: " + e.getMessage());
        }
    }
}
