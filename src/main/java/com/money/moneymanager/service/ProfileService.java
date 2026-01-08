package com.money.moneymanager.service;

import com.money.moneymanager.dto.AuthDTO;
import com.money.moneymanager.dto.ProfileDTO;
import com.money.moneymanager.entity.ProfileEntity;
import com.money.moneymanager.repositorty.ProfileRepository;
import com.money.moneymanager.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ProfileDTO registerProfile(ProfileDTO profileDTO) {
        ProfileEntity newProfile = toEntity(profileDTO);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile= profileRepository.save(newProfile);

        //Email Sending
        String activationLink = "http://localhost:8080/api/v1.0/activate?token=" + newProfile.getActivationToken();
        String subject = "Activate your Money Manager account";
        String body = "Click the following link to activate your account: " + activationLink;
        emailService.sendEmail(newProfile.getEmail(), subject, body);


        return toDTO(newProfile);
    }

    public ProfileEntity toEntity(ProfileDTO profileDTO) {
        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(passwordEncoder.encode(profileDTO.getPassword()))
                .profileImageUrl(profileDTO.getProfileImageUrl())
                .createdAt(profileDTO.getCreatedAt())
                .updatedAt(profileDTO.getUpdatedAt())
                .build();
    }

    public ProfileDTO toDTO(ProfileEntity profileEntity) {
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }
    //validate the token

    public boolean activateProfile(String activationToken) {
        return profileRepository.findByActivationToken(activationToken)
                .map(profile -> {
            profile.setIsActive(true);
            profileRepository.save(profile);//update the data
            return true;
        }).orElse(false);
    }

    public boolean isAccountActive(String email) {
        return profileRepository.findByEmail(email)
                .map(ProfileEntity::getIsActive)
                .orElse(false);
    }

    public ProfileEntity getCurrentProfile(){
       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
         return profileRepository.findByEmail(authentication.getName())
                 .orElseThrow(()-> new UsernameNotFoundException("Profile not found with email: "+ authentication.getName()));
    }

    public ProfileDTO getPublicProfile(String email){
        ProfileEntity currentUSer = null;
        if (email == null) {
            currentUSer= getCurrentProfile();
        }else {
            currentUSer = profileRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: " + email));
        }
        return ProfileDTO.builder()
                .id(currentUSer.getId())
                .fullName(currentUSer.getFullName())
                .email(currentUSer.getEmail())
                .profileImageUrl(currentUSer.getProfileImageUrl())
                .createdAt(currentUSer.getCreatedAt())
                .updatedAt(currentUSer.getUpdatedAt())
                .build();
    }

    public Map<String, Object> authenticateAndGenerateToken(AuthDTO authDTO) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
            //Generate the JWT token
            String token = jwtUtil.generateToken(authDTO.getEmail());

            return Map.of(
                    "token",token,
                    "user",getPublicProfile(authDTO.getEmail())
            );

        }catch (Exception e){
            throw new RuntimeException("Invalid email or password");
        }
    }


}
