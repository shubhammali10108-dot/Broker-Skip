package com.brokerskip.controller;

import com.brokerskip.dto.ApiResponse;
import com.brokerskip.dto.PropertyDTO;
import com.brokerskip.dto.PropertyRequest;
import com.brokerskip.service.PropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/properties")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<ApiResponse<PropertyDTO>> createProperty(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody PropertyRequest request) {
        log.info("Creating new property");
        Integer userId = 1; // Extract from token in production
        PropertyDTO propertyDTO = propertyService.createProperty(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Property created successfully", propertyDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getAllProperties() {
        log.info("Fetching all properties");
        List<PropertyDTO> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(ApiResponse.success("Properties fetched successfully", properties));
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<PropertyDTO>> getProperty(@PathVariable Integer propertyId) {
        log.info("Fetching property with id: {}", propertyId);
        PropertyDTO propertyDTO = propertyService.getProperty(propertyId);
        return ResponseEntity.ok(ApiResponse.success("Property fetched successfully", propertyDTO));
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<PropertyDTO>> updateProperty(
            @PathVariable Integer propertyId,
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody PropertyRequest request) {
        log.info("Updating property with id: {}", propertyId);
        Integer userId = 1; // Extract from token in production
        PropertyDTO propertyDTO = propertyService.updateProperty(propertyId, userId, request);
        return ResponseEntity.ok(ApiResponse.success("Property updated successfully", propertyDTO));
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<?>> deleteProperty(
            @PathVariable Integer propertyId,
            @RequestHeader("Authorization") String token) {
        log.info("Deleting property with id: {}", propertyId);
        Integer userId = 1; // Extract from token in production
        propertyService.deleteProperty(propertyId, userId);
        return ResponseEntity.ok(ApiResponse.success("Property deleted successfully"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getUserProperties(@PathVariable Integer userId) {
        log.info("Fetching properties for user: {}", userId);
        List<PropertyDTO> properties = propertyService.getUserProperties(userId);
        return ResponseEntity.ok(ApiResponse.success("User properties fetched successfully", properties));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getPropertiesByCity(@PathVariable String city) {
        log.info("Fetching properties in city: {}", city);
        List<PropertyDTO> properties = propertyService.getPropertiesByCity(city);
        return ResponseEntity.ok(ApiResponse.success("Properties fetched successfully", properties));
    }
}
