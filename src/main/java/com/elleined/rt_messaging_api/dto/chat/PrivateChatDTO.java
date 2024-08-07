package com.elleined.rt_messaging_api.dto.chat;

import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PrivateChatDTO extends ChatDTO {
    private UserDTO receiverDTO;

    @Builder
    public PrivateChatDTO(int id, LocalDateTime createdAt, UserDTO creatorDTO, UserDTO receiverDTO) {
        super(id, createdAt, creatorDTO);
        this.receiverDTO = receiverDTO;
    }

    @Override
    public PrivateChatDTO addLinks(User currentUser, boolean doInclude) {
        super.addLinks(currentUser, doInclude);
        return this;
    }
}
