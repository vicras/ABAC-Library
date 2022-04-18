package com.vicras.abaclib.use.dto;

import com.vicras.abaclib.use.model.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class NewUserDTO {

    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private Position position;
}
