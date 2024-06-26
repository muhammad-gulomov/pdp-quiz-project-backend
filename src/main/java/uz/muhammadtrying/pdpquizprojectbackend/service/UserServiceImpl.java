package uz.muhammadtrying.pdpquizprojectbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.TempUser;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.UserService;
import uz.muhammadtrying.pdpquizprojectbackend.repo.TempUserRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.UserRepository;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final TempUserRepository tempUserRepository;


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

    @Override
    public void save(TempUser tempUser) {
        User user = User.builder()
                .firstName(tempUser.getFirstName())
                .lastName(tempUser.getLastName())
                .email(tempUser.getEmail())
                .password(tempUser.getPassword())
                .build();
        userRepository.save(user);
    }

    @Override
    public void addDataToTempDB(UserDTO userDTO, String code) {
        TempUser tempUser = TempUser.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .code(code)
                .build();
        tempUserRepository.save(tempUser);
    }

    @Override
    public TempUser getDataFromTempDB(String email) {
        return tempUserRepository.findByEmail(email);
    }
}
