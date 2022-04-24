package com.vicras.blog.dto;

import com.vicras.blog.model.Department;
import com.vicras.blog.model.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String login;
    private Position position;
    private Department department;
}
