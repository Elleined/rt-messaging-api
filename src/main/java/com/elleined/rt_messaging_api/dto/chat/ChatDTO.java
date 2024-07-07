package com.elleined.rt_messaging_api.dto.chat;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.model.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ChatDTO extends DTO {
    private UserDTO creatorDTO;


    public ChatDTO(int id, LocalDateTime createdAt, UserDTO creatorDTO) {
        super(id, createdAt);
        this.creatorDTO = creatorDTO;
    }

    @Override
    protected List<Link> getAllRelatedLinks(User currentUser, boolean doInclude) {
        return List.of();
    }

    @Override
    protected List<Link> getAllSelfLinks(User currentUser, boolean doInclude) {
        return List.of();
    }
}
