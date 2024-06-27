package uz.muhammadtrying.pdpquizprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.muhammadtrying.pdpquizprojectbackend.dto.CodeDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.LogInDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.TokenDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.TempUser;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;
import uz.muhammadtrying.pdpquizprojectbackend.security.CustomUserDetailsService;
import uz.muhammadtrying.pdpquizprojectbackend.utils.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LogInDTO logInDTO) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(logInDTO.getEmail(), logInDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO("Bearer " + jwtUtil.generateAccessToken(userDetails), "Bearer " + jwtUtil.generateRefreshToken(logInDTO)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> accessTokenGeneratorUsingRefreshToken() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body("Bearer " + jwtUtil.generateAccessToken(userDetails));
    }

    @PostMapping("/signup")
    public void getInfoAndSendCode(@RequestBody UserDTO userDTO) {
        String code = userService.codeGenerator();
        userService.sendEmail(userDTO.getEmail(), code);
        userService.addDataToTempDB(userDTO, code);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> getCodeAndVerify(@RequestBody CodeDTO codeDTO) {
        String email = codeDTO.getEmail();
        String code = codeDTO.getCode();
        TempUser tempUser = userService.getDataFromTempDB(email);

        if (code.equals(tempUser.getCode())) {
            userService.save(tempUser);
            return ResponseEntity.ok("Verification successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code");
        }
    }
}
