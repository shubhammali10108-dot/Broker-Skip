package com.brokerskip.service;

import com.brokerskip.dto.FavoriteDTO;
import com.brokerskip.dto.PropertyDTO;

import java.util.List;

public interface FavoriteService {

    FavoriteDTO addFavorite(Integer userId, Integer propertyId);

    void removeFavorite(Integer userId, Integer propertyId);

    List<FavoriteDTO> getUserFavorites(Integer userId);

    List<PropertyDTO> getUserFavoriteProperties(Integer userId);

    boolean isFavorite(Integer userId, Integer propertyId);
}
