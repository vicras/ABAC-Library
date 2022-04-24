package com.vicras.blog.mapper;

import static org.mapstruct.ReportingPolicy.ERROR;

import com.vicras.blog.dto.UserDTO;
import com.vicras.blog.model.CommonUser;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public abstract class UserMapper {

    public abstract UserDTO toResponse(CommonUser user);

    public abstract List<UserDTO> toResponse(List<CommonUser> all);
}