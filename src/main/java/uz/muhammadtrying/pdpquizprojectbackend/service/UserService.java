package uz.muhammadtrying.pdpquizprojectbackend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;

@Service
public interface UserService {

    String sendEmail(String email);

    void addDataToSession(HttpSession httpSession, UserDTO userDTO, String code);
}
