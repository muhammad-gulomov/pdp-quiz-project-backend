package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.TempUser;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;

@Service
public interface UserService {

    void sendEmail(String email, String code);

    String codeGenerator();

    User getDataFromSession(HttpSession httpSession);

    void save(TempUser user);

    void addDataToTempDB(UserDTO userDTO, String code);

    TempUser getDataFromTempDB(String email);
}
