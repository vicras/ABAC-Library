package com.vicras.blog.dto;

import com.vicras.blog.model.Department;
import com.vicras.blog.model.Position;
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
    @NotEmpty
    private Department department;
}
