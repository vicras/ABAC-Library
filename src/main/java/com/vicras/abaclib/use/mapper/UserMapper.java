package com.vicras.abaclib.use.mapper;

import static org.mapstruct.ReportingPolicy.ERROR;

import com.vicras.abaclib.use.dto.UserDTO;
import com.vicras.abaclib.use.model.CommonUser;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public abstract class UserMapper {

    @Mapping(target = "password", ignore = true)
    public abstract UserDTO toResponse(CommonUser user);

    public abstract List<UserDTO> toResponse(List<CommonUser> all);
}