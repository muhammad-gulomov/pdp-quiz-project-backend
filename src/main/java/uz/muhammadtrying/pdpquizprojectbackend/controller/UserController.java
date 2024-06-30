package uz.muhammadtrying.pdpquizprojectbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserCredDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserInfoDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserSettingsDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;
import uz.muhammadtrying.pdpquizprojectbackend.security.CustomUserDetailsService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        User user = (User) customUserDetailsService.loadUserByUsername(email);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setFirstName(user.getFirstName());
        userInfoDTO.setLastName(user.getLastName());
        userInfoDTO.setPhotoUrl(user.getPhoto());
        return ResponseEntity.ok(userInfoDTO);
    }

    @GetMapping("/info/get")
    public ResponseEntity<UserSettingsDTO> changeCredentialsPage() {
        User currentUser = userService.getCurrentUser();

        UserSettingsDTO userSettingsDTO = new UserSettingsDTO();
        userSettingsDTO.setPhoto(currentUser.getPhoto());
        userSettingsDTO.setEmail(currentUser.getEmail());
        userSettingsDTO.setFullName(currentUser.getFirstName() + " " + currentUser.getLastName());

        return ResponseEntity.ok(userSettingsDTO);
    }

    @PostMapping("/info/change")
    public ResponseEntity<String> changeCredentials(@RequestBody UserCredDTO userCredDTO) {
        User currentUser = userService.getCurrentUser();

        if (userCredDTO.getPassword() != null && !userCredDTO.getPassword().equals(userCredDTO.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        userService.checkIfValidAndSet(userCredDTO, currentUser);

        userService.save(currentUser);

        return ResponseEntity.ok("Personal information updated successfully");
    }


}
