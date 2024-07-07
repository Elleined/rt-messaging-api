package com.elleined.rt_messaging_api.dto.chat;

import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupChatDTO extends ChatDTO {
    private String name;
    private String picture;

    @Builder
    public GroupChatDTO(int id, LocalDateTime createdAt, UserDTO creatorDTO, String name, String picture) {
        super(id, createdAt, creatorDTO);
        this.name = name;
        this.picture = picture;
    }

    @Override
    public GroupChatDTO addLinks(User currentUser, boolean doInclude) {
        super.addLinks(currentUser, doInclude);
        return this;
    }
}
