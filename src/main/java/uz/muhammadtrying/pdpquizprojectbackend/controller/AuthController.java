package uz.muhammadtrying.pdpquizprojectbackend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.muhammadtrying.pdpquizprojectbackend.dto.LogInDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.TokenDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
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

    @PostMapping("/signup")
    public String getInfoAndSendCode(@RequestBody UserDTO userDTO) {
        String code = userService.sendEmail(userDTO.getEmail());
        userService.addDataToSession(httpSession,userDTO,code);
        return code;
    }

    @PostMapping("/refresh")
    public String accessTokenGeneratorUsingRefreshToken() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return "Bearer " + jwtUtil.generateAccessToken(userDetails);
    }
}
