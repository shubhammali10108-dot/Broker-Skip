package com.brokerskip.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyDTO {

    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private String propertyType;
    private BigDecimal price;
    private BigDecimal area;
    private Integer bedrooms;
    private Integer bathrooms;
    private String location;
    private String city;
    private String state;
    private String pincode;
    private List<String> imageUrls;
    private List<String> amenities;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO owner;
}
