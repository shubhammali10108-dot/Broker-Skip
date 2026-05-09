package com.brokerskip.service.impl;

import com.brokerskip.dto.FavoriteDTO;
import com.brokerskip.dto.PropertyDTO;
import com.brokerskip.entity.Favorite;
import com.brokerskip.entity.Property;
import com.brokerskip.entity.User;
import com.brokerskip.exception.DuplicateResourceException;
import com.brokerskip.exception.ResourceNotFoundException;
import com.brokerskip.repository.FavoriteRepository;
import com.brokerskip.repository.PropertyRepository;
import com.brokerskip.service.FavoriteService;
import com.brokerskip.service.PropertyService;
import com.brokerskip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyService propertyService;

    @Override
    public FavoriteDTO addFavorite(Integer userId, Integer propertyId) {
        log.info("Adding property {} to favorites for userId: {}", propertyId, userId);
        
        // Verify user exists
        userService.getUserEntity(userId);
        
        // Verify property exists
        propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", propertyId));
        
        // Check if already favorited
        if (favoriteRepository.existsByUserIdAndPropertyId(userId, propertyId)) {
            throw new DuplicateResourceException("This property is already in your favorites");
        }
        
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setPropertyId(propertyId);
        
        Favorite savedFavorite = favoriteRepository.save(favorite);
        log.info("Property added to favorites successfully");
        
        return mapToDTO(savedFavorite);
    }

    @Override
    public void removeFavorite(Integer userId, Integer propertyId) {
        log.info("Removing property {} from favorites for userId: {}", propertyId, userId);
        
        Favorite favorite = favoriteRepository.findByUserIdAndPropertyId(userId, propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite", "property", propertyId));
        
        favoriteRepository.delete(favorite);
        log.info("Property removed from favorites successfully");
    }

    @Override
    public List<FavoriteDTO> getUserFavorites(Integer userId) {
        log.info("Fetching favorites for userId: {}", userId);
        
        return favoriteRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyDTO> getUserFavoriteProperties(Integer userId) {
        log.info("Fetching favorite properties for userId: {}", userId);
        
        return favoriteRepository.findByUserId(userId).stream()
                .map(favorite -> propertyService.getProperty(favorite.getPropertyId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isFavorite(Integer userId, Integer propertyId) {
        return favoriteRepository.existsByUserIdAndPropertyId(userId, propertyId);
    }

    private FavoriteDTO mapToDTO(Favorite favorite) {
        return FavoriteDTO.builder()
                .id(favorite.getId())
                .userId(favorite.getUserId())
                .propertyId(favorite.getPropertyId())
                .createdAt(favorite.getCreatedAt())
                .build();
    }
}
