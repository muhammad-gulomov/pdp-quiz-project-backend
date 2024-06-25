package uz.muhammadtrying.pdpquizprojectbackend.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JavaMailSender javaMailSender;


    @Override
    @Async
    public void sendEmail(String email, String code) {
        System.out.println(Thread.currentThread().getName());
        sendMail(email, code);
    }

    public void sendMail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("muhammadtrying@gmail.com");
        message.setTo(email);
        message.setSubject("Authentication");
        message.setText("Your code is " + code);
        javaMailSender.send(message);
    }

    @Override
    public void addDataToSession(HttpSession httpSession, UserDTO userDTO, String code) {
        httpSession.setAttribute("code", code);
        httpSession.setAttribute("firstName", userDTO.getFirstName());
        httpSession.setAttribute("lastName", userDTO.getLastName());
        httpSession.setAttribute("password", userDTO.getPassword());
        httpSession.setAttribute("email", userDTO.getEmail());
    }

    @Override
    public String codeGenerator() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int CODE_LENGTH = 4;
        Random random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
