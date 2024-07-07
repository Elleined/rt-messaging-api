package com.elleined.rt_messaging_api.dto.mention;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MentionDTO extends DTO {
    private UserDTO creatorDTO;
    private UserDTO mentionedUserDTO;
    private MessageDTO messageDTO;

    @Builder
    public MentionDTO(int id,
                      LocalDateTime createdAt,
                      UserDTO creatorDTO,
                      UserDTO mentionedUserDTO,
                      MessageDTO messageDTO) {
        super(id, createdAt);
        this.creatorDTO = creatorDTO;
        this.mentionedUserDTO = mentionedUserDTO;
        this.messageDTO = messageDTO;
    }

    @Override
    public MentionDTO addLinks(User currentUser, boolean doInclude) {
        super.addLinks(currentUser, doInclude);
        return this;
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
