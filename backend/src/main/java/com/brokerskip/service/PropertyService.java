package com.brokerskip.service;

import com.brokerskip.dto.PropertyDTO;
import com.brokerskip.dto.PropertyRequest;

import java.util.List;

public interface PropertyService {

    PropertyDTO createProperty(Integer userId, PropertyRequest request);

    PropertyDTO getProperty(Integer propertyId);

    PropertyDTO updateProperty(Integer propertyId, Integer userId, PropertyRequest request);

    void deleteProperty(Integer propertyId, Integer userId);

    List<PropertyDTO> getAllProperties();

    List<PropertyDTO> getUserProperties(Integer userId);

    List<PropertyDTO> getPropertiesByCity(String city);

    List<PropertyDTO> getPropertiesByType(String type);
}
