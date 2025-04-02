package com.louiskhanh.airbnb_clone_be.user.mapper;

import org.mapstruct.Mapper;

import com.louiskhanh.airbnb_clone_be.user.application.dto.ReadUserDTO;
import com.louiskhanh.airbnb_clone_be.user.domain.Authority;
import com.louiskhanh.airbnb_clone_be.user.domain.User;

@Mapper(componentModel = "spring")
public interface UserMapper {   
    ReadUserDTO readUserDTOToUser(User user);

    default String mapAuthoritiesToString(Authority authority) {
        return authority.getName();
    }
    
}