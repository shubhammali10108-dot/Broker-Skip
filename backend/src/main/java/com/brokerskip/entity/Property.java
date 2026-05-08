package com.brokerskip.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "property_type")
    private String propertyType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private BigDecimal area;

    @Column
    private Integer bedrooms = 0;

    @Column
    private Integer bathrooms = 0;

    @Column
    private String location;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String pincode;

    @Column(columnDefinition = "JSON")
    private String imageUrls;

    @Column(columnDefinition = "JSON")
    private String amenities;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
