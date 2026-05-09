package com.brokerskip.repository;

import com.brokerskip.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OTP, Integer> {

    Optional<OTP> findTopByPhoneNumberOrderByCreatedAtDesc(String phoneNumber);

    Optional<OTP> findByPhoneNumberAndOtpCode(String phoneNumber, String otpCode);
}
