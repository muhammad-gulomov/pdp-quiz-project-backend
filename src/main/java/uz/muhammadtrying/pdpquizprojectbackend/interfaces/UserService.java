package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.TempUser;

@Service
public interface UserService {

    void sendEmail(String email, String code);

    String codeGenerator();

    void save(TempUser user);

    void addDataToTempDB(UserDTO userDTO, String code);

    TempUser getDataFromTempDB(String email);
}
