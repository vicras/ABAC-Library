package com.vicras.abaclib.use.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTokenDTO {
    private String token;
    private UserDTO user;
}