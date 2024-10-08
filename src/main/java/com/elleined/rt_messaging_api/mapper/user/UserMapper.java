package com.elleined.rt_messaging_api.mapper.user;

import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.mapper.CustomMapper;
import com.elleined.rt_messaging_api.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper extends CustomMapper<User, UserDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "image", source = "image")
    })
    UserDTO toDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "image", source = "image"),
            @Mapping(target = "receivedGroupChats", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "createdPrivateChats", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "receivedPrivateChats", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "messages", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "reactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "receivedMentions", expression = "java(new java.util.ArrayList<>())"),
    })
    User toEntity(String name,
                  String image,
                  String email,
                  String password);
}
