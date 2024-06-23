package uz.muhammadtrying.pdpquizprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogInDTO {
    private String email;
    private String password;
}
