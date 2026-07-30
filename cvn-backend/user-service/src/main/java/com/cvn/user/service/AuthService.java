package com.cvn.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvn.user.dto.request.LoginRequest;
import com.cvn.user.dto.request.RefreshTokenRequest;
import com.cvn.user.dto.request.RegisterClinicRequest;
import com.cvn.user.dto.request.RegisterParentRequest;
import com.cvn.user.dto.response.AuthResponse;
import com.cvn.user.entity.Clinic;
import com.cvn.user.entity.Parent;
import com.cvn.user.entity.RefreshToken;
import com.cvn.user.entity.User;
import com.cvn.user.enums.ClinicStatus;
import com.cvn.user.enums.Role;
import com.cvn.user.enums.UserStatus;
import com.cvn.user.exception.InvalidRequestException;
import com.cvn.user.exception.ResourceAlreadyExistsException;
import com.cvn.user.exception.ResourceNotFoundException;
import com.cvn.user.repository.ClinicRepository;
import com.cvn.user.repository.ParentRepository;
import com.cvn.user.repository.UserRepository;
import com.cvn.user.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final ClinicRepository clinicRepository;
    private final AuthenticationManager authenticationManager;

    
    /*
     * Register Parent
     */
    
    public AuthResponse registerParent(RegisterParentRequest request) {

        validateUser(request.getEmail(), request.getPhone());

        User user = createParentUser(request);

        userRepository.save(user);

        Parent parent = new Parent();

        parent.setMyUser(user);
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());

        parentRepository.save(parent);

        String accessToken = jwtService.generateAccessToken(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .role(user.getRole())
                .message("Parent registered successfully.")
                .build();
    }
    
    private void validateUser(String email, String phone) {
        if (userRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException(
                    "Email already registered.");
        }

        if (userRepository.existsByPhone(phone)) {
            throw new ResourceAlreadyExistsException(
                    "Phone number already registered.");
        }
    }
    
    private User createParentUser(RegisterParentRequest request) {

        User user = new User();

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword()));

        user.setPhone(request.getPhone());

        user.setRole(Role.ROLE_PARENT);

        user.setStatus(UserStatus.ACTIVE);

        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setPincode(request.getPincode());

        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());

        return user;
    }
    
    
    /*
     * Register Clinic
     */
    
    public AuthResponse registerClinic(RegisterClinicRequest request) {

        validateUser(request.getEmail(), request.getPhone());

        User user = createClinicUser(request);

        userRepository.save(user);

        Clinic clinic = new Clinic();

        clinic.setMyUser(user);
        clinic.setClinicName(request.getClinicName());
        clinic.setLicenseNumber(request.getLicenseNumber());

        // If admin approval is required
        clinic.setStatus(ClinicStatus.PENDING);

        clinicRepository.save(clinic);

        String accessToken = jwtService.generateAccessToken(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .role(user.getRole())
                .message("Clinic registered successfully.")
                .build();
    }
    
    private User createClinicUser(RegisterClinicRequest request) {

        User user = new User();

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword()));

        user.setPhone(request.getPhone());

        user.setRole(Role.ROLE_CLINIC);

        user.setStatus(UserStatus.ACTIVE);

        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setPincode(request.getPincode());

        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());

        return user;
    }
    
    /*
     * login() method
     */
    
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        // Optional Business Rule
        if (user.getRole() == Role.ROLE_CLINIC) {

            Clinic clinic = clinicRepository.findByMyUser(user)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Clinic not found."));

            if (clinic.getStatus() == ClinicStatus.PENDING) {
                throw new InvalidRequestException(
                        "Clinic account is pending admin approval.");
            }

            if (clinic.getStatus() == ClinicStatus.REJECTED) {
                throw new InvalidRequestException(
                        "Clinic registration has been rejected.");
            }
        }

        String accessToken = jwtService.generateAccessToken(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .role(user.getRole())
                .message("Login successful.")
                .build();
    }
    
    /*
     * Refresh Token
     */
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken());

        User user = refreshToken.getUser();

        String accessToken =
                jwtService.generateAccessToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .role(user.getRole())
                .message("Access token refreshed successfully.")
                .build();
    }
    
    /*
     * Logout
     */
    public void logout(RefreshTokenRequest request) {

        refreshTokenService.revokeRefreshToken(
                request.getRefreshToken());
    }

}

