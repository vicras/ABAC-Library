package com.vicras.abaclib.use.dto;

import com.vicras.abaclib.use.model.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private Position position;
}
