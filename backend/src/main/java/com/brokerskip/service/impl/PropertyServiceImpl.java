package com.brokerskip.service.impl;

import com.brokerskip.dto.PropertyDTO;
import com.brokerskip.dto.PropertyRequest;
import com.brokerskip.dto.UserDTO;
import com.brokerskip.entity.Property;
import com.brokerskip.entity.User;
import com.brokerskip.exception.ResourceNotFoundException;
import com.brokerskip.exception.UnauthorizedException;
import com.brokerskip.repository.PropertyRepository;
import com.brokerskip.service.PropertyService;
import com.brokerskip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;

    @Override
    public PropertyDTO createProperty(Integer userId, PropertyRequest request) {
        log.info("Creating property for userId: {}", userId);
        
        User user = userService.getUserEntity(userId);
        
        Property property = new Property();
        property.setUserId(userId);
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setPropertyType(request.getPropertyType());
        property.setPrice(request.getPrice());
        property.setArea(request.getArea());
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setLocation(request.getLocation());
        property.setCity(request.getCity());
        property.setState(request.getState());
        property.setPincode(request.getPincode());
        
        if (request.getImageUrls() != null) {
            property.setImageUrls(String.join(",", request.getImageUrls()));
        }
        
        if (request.getAmenities() != null) {
            property.setAmenities(String.join(",", request.getAmenities()));
        }
        
        property.setIsActive(true);
        
        Property savedProperty = propertyRepository.save(property);
        log.info("Property created successfully with id: {}", savedProperty.getId());
        
        return mapToDTO(savedProperty, user);
    }

    @Override
    public PropertyDTO getProperty(Integer propertyId) {
        log.info("Fetching property with id: {}", propertyId);
        
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", propertyId));
        
        User owner = userService.getUserEntity(property.getUserId());
        
        return mapToDTO(property, owner);
    }

    @Override
    public PropertyDTO updateProperty(Integer propertyId, Integer userId, PropertyRequest request) {
        log.info("Updating property with id: {} for userId: {}", propertyId, userId);
        
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", propertyId));
        
        if (!property.getUserId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to update this property");
        }
        
        if (request.getTitle() != null) property.setTitle(request.getTitle());
        if (request.getDescription() != null) property.setDescription(request.getDescription());
        if (request.getPrice() != null) property.setPrice(request.getPrice());
        if (request.getArea() != null) property.setArea(request.getArea());
        if (request.getBedrooms() != null) property.setBedrooms(request.getBedrooms());
        if (request.getBathrooms() != null) property.setBathrooms(request.getBathrooms());
        if (request.getCity() != null) property.setCity(request.getCity());
        if (request.getState() != null) property.setState(request.getState());
        if (request.getPincode() != null) property.setPincode(request.getPincode());
        
        Property updatedProperty = propertyRepository.save(property);
        
        User owner = userService.getUserEntity(property.getUserId());
        return mapToDTO(updatedProperty, owner);
    }

    @Override
    public void deleteProperty(Integer propertyId, Integer userId) {
        log.info("Deleting property with id: {} for userId: {}", propertyId, userId);
        
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", propertyId));
        
        if (!property.getUserId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to delete this property");
        }
        
        propertyRepository.delete(property);
        log.info("Property deleted successfully with id: {}", propertyId);
    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        log.info("Fetching all active properties");
        
        return propertyRepository.findByIsActiveTrue().stream()
                .map(property -> {
                    User owner = userService.getUserEntity(property.getUserId());
                    return mapToDTO(property, owner);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyDTO> getUserProperties(Integer userId) {
        log.info("Fetching properties for userId: {}", userId);
        
        User user = userService.getUserEntity(userId);
        
        return propertyRepository.findByUserId(userId).stream()
                .map(property -> mapToDTO(property, user))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyDTO> getPropertiesByCity(String city) {
        log.info("Fetching properties in city: {}", city);
        
        return propertyRepository.findActivePropertiesByCity(city).stream()
                .map(property -> {
                    User owner = userService.getUserEntity(property.getUserId());
                    return mapToDTO(property, owner);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyDTO> getPropertiesByType(String type) {
        log.info("Fetching properties of type: {}", type);
        
        return propertyRepository.findActivePropertiesByType(type).stream()
                .map(property -> {
                    User owner = userService.getUserEntity(property.getUserId());
                    return mapToDTO(property, owner);
                })
                .collect(Collectors.toList());
    }

    private PropertyDTO mapToDTO(Property property, User owner) {
        UserDTO ownerDTO = UserDTO.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .city(owner.getCity())
                .state(owner.getState())
                .phoneNumber(owner.getPhoneNumber())
                .build();
        
        List<String> imageUrls = property.getImageUrls() != null ? 
                List.of(property.getImageUrls().split(",")) : null;
        
        List<String> amenities = property.getAmenities() != null ? 
                List.of(property.getAmenities().split(",")) : null;
        
        return PropertyDTO.builder()
                .id(property.getId())
                .userId(property.getUserId())
                .title(property.getTitle())
                .description(property.getDescription())
                .propertyType(property.getPropertyType())
                .price(property.getPrice())
                .area(property.getArea())
                .bedrooms(property.getBedrooms())
                .bathrooms(property.getBathrooms())
                .location(property.getLocation())
                .city(property.getCity())
                .state(property.getState())
                .pincode(property.getPincode())
                .imageUrls(imageUrls)
                .amenities(amenities)
                .isActive(property.getIsActive())
                .owner(ownerDTO)
                .createdAt(property.getCreatedAt())
                .updatedAt(property.getUpdatedAt())
                .build();
    }
}
