package uz.muhammadtrying.pdpquizprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredDTO {
    private String firstName;
    private String lastName;
    private String password;
    private byte[] photo;
}
