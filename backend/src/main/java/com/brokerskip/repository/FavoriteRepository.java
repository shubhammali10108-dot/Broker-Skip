package com.brokerskip.repository;

import com.brokerskip.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Favorite> findByUserId(Integer userId);

    Optional<Favorite> findByUserIdAndPropertyId(Integer userId, Integer propertyId);

    boolean existsByUserIdAndPropertyId(Integer userId, Integer propertyId);

    void deleteByUserIdAndPropertyId(Integer userId, Integer propertyId);
}
