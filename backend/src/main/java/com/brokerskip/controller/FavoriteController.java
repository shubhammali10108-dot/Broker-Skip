package com.brokerskip.controller;

import com.brokerskip.dto.ApiResponse;
import com.brokerskip.dto.FavoriteDTO;
import com.brokerskip.dto.PropertyDTO;
import com.brokerskip.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<ApiResponse<FavoriteDTO>> addFavorite(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer userId,
            @RequestParam Integer propertyId) {
        log.info("Adding property {} to favorites", propertyId);
        FavoriteDTO favoriteDTO = favoriteService.addFavorite(userId, propertyId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Added to favorites", favoriteDTO));
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<?>> removeFavorite(
            @PathVariable Integer propertyId,
            @RequestHeader("Authorization") String token,
            @RequestParam Integer userId) {
        log.info("Removing property {} from favorites", propertyId);
        favoriteService.removeFavorite(userId, propertyId);
        return ResponseEntity.ok(ApiResponse.success("Removed from favorites"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getUserFavorites(@PathVariable Integer userId) {
        log.info("Fetching favorites for user: {}", userId);
        List<PropertyDTO> favorites = favoriteService.getUserFavoriteProperties(userId);
        return ResponseEntity.ok(ApiResponse.success("Favorites fetched successfully", favorites));
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Boolean>> checkFavorite(
            @RequestParam Integer userId,
            @RequestParam Integer propertyId) {
        log.info("Checking if property {} is favorite for user {}", propertyId, userId);
        boolean isFavorite = favoriteService.isFavorite(userId, propertyId);
        return ResponseEntity.ok(ApiResponse.success("Check completed", isFavorite));
    }
}
