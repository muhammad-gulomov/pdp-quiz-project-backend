package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserCredDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserSettingsDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

        userService.checkIfValidAndSet(userCredDTO, currentUser);

        userService.save(currentUser);

        return ResponseEntity.ok("Personal information updated successfully");
    }
}
