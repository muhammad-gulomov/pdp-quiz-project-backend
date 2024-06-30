package uz.muhammadtrying.pdpquizprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredDTO {
    private byte[] photo;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
}
