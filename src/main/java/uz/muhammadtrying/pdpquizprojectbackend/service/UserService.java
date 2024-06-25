package uz.muhammadtrying.pdpquizprojectbackend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;

@Service
public interface UserService {

    void sendEmail(String email, String code);

    void addDataToSession(HttpSession httpSession, UserDTO userDTO, String code);

    String codeGenerator();
}
