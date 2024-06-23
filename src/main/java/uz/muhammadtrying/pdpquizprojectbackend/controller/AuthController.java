package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.muhammadtrying.pdpquizprojectbackend.dto.LogInDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.TokenDTO;
import uz.muhammadtrying.pdpquizprojectbackend.utils.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LogInDTO logInDTO) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(logInDTO.getEmail(), logInDTO.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return new TokenDTO("Bearer " + jwtUtil.generateAccessToken(logInDTO), "Bearer " + jwtUtil.generateRefreshToken(logInDTO));
    }
}
