package com.brokerskip.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavoriteDTO {

    private Integer id;
    private Integer userId;
    private Integer propertyId;
    private PropertyDTO property;
    private LocalDateTime createdAt;
}
