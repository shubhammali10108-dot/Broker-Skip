package com.brokerskip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpResponse {

    private boolean success;
    private String message;
    private String token;
    private Integer userId;
    private boolean profileExists;
    private UserDTO user;
}
