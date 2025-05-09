package org.example.orishw2.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationForm {

    private String username;
    private String password;
    private String email;


}
