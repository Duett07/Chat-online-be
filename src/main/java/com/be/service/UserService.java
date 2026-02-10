package com.be.service;

import com.be.dto.UpdateDTO;
import com.be.entity.User;
import com.be.enums.GenderStatus;
import com.be.payload.ProfileResponse;
import com.be.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private  final AuthService authService;

    public ProfileResponse getProfile() {
        UUID userId = authService.getCurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        return new ProfileResponse(user.getDisplayName(), user.getGender(), user.getDateOfBirth(), user.getImage(), user.getUpdatedAt());
    }

    public ProfileResponse updateProfile(UpdateDTO data) {
        UUID userId = authService.getCurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        if(data.displayName() != null && !data.displayName().isEmpty()){
            user.setDisplayName(data.displayName());
        }

        if (data.gender().equals("Male")) {
            user.setGender(GenderStatus.Male);
        }  else if  (data.gender().equals("Female")) {
            user.setGender(GenderStatus.Female);
        }

        if(data.dateOfBirth() != null){
            user.setDateOfBirth(data.dateOfBirth());
        }

        this.userRepository.save(user);

        return new ProfileResponse(user.getDisplayName(), user.getGender(), user.getDateOfBirth() != null ? user.getDateOfBirth().atStartOfDay().toLocalDate() : null, user.getImage(), user.getUpdatedAt());
    }
}
