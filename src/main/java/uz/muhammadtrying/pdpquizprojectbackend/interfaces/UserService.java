package uz.muhammadtrying.pdpquizprojectbackend.interfaces;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserCredDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.TempUser;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;

import java.util.Optional;

@Service
public interface UserService {

    void sendEmail(String email, String code);

    String codeGenerator();

    void save(TempUser user);

    void save(User user);

    void addDataToTempDB(UserDTO userDTO, String code);

    TempUser getDataFromTempDB(String email);

    User getCurrentUser();

    void checkIfValidAndSet(UserCredDTO userCredDTO, User currentUser);
}
