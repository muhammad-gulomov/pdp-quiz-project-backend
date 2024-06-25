package uz.muhammadtrying.pdpquizprojectbackend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.muhammadtrying.pdpquizprojectbackend.dto.LogInDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.TokenDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;
import uz.muhammadtrying.pdpquizprojectbackend.security.CustomUserDetailsService;
import uz.muhammadtrying.pdpquizprojectbackend.service.UserService;
import uz.muhammadtrying.pdpquizprojectbackend.utils.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final HttpSession httpSession;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LogInDTO logInDTO) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(logInDTO.getEmail(), logInDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        return new TokenDTO("Bearer " + jwtUtil.generateAccessToken(userDetails), "Bearer " + jwtUtil.generateRefreshToken(logInDTO));
    }

    @PostMapping("/refresh")
    public String accessTokenGeneratorUsingRefreshToken() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return "Bearer " + jwtUtil.generateAccessToken(userDetails);
    }

    @PostMapping("/signup")
    public void getInfoAndSendCode(@RequestBody UserDTO userDTO) {
        String code = userService.codeGenerator();
        userService.sendEmail(userDTO.getEmail(), code);
        userService.addDataToSession(httpSession, userDTO, code);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> getCodeAndVerify(@RequestParam String code) {
        String verificationCode = String.valueOf(httpSession.getAttribute("code"));

        if (verificationCode.equals(code)) {
            User authenticatedUser = userService.getDataFromSession(httpSession);
            userService.save(authenticatedUser);
            return ResponseEntity.ok("Verification successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code");
        }
    }
}
