package com.brokerskip.repository;

import com.brokerskip.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findByUserId(Integer userId);

    List<Property> findByCity(String city);

    List<Property> findByPropertyType(String propertyType);

    List<Property> findByIsActiveTrue();

    @Query("SELECT p FROM Property p WHERE p.city = :city AND p.isActive = true")
    List<Property> findActivePropertiesByCity(@Param("city") String city);

    @Query("SELECT p FROM Property p WHERE p.propertyType = :type AND p.isActive = true")
    List<Property> findActivePropertiesByType(@Param("type") String type);
}
