package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;

@Service
public interface UserService {

    void sendEmail(String email, String code);

    void addDataToSession(HttpSession httpSession, UserDTO userDTO, String code);

    String codeGenerator();

    User getDataFromSession(HttpSession httpSession);

    void save(User user);
}
