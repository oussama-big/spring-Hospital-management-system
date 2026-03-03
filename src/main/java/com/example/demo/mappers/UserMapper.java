package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.UserProfileResponse;
import com.example.demo.entities.User;
import com.example.demo.entities.Role;



@Mapper(componentModel = "spring")
public interface UserMapper {

   @Mapping(target = "role", source = "role")
    UserProfileResponse toResponse(User user);

    default String mapRoleToString(Role role) {
        if (role == null || role.getName() == null) {
            return null;
        }
        // This handles the conversion: Role -> Enum -> String
        return role.getName().name(); 
    }

}
