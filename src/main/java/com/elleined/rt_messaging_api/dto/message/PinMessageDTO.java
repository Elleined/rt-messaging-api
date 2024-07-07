package com.elleined.rt_messaging_api.dto.message;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.dto.chat.ChatDTO;
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
public class PinMessageDTO extends DTO {
    private UserDTO creatorDTO;
    private MessageDTO messageDTO;
    private ChatDTO chatDTO;

    @Builder
    public PinMessageDTO(int id,
                         LocalDateTime createdAt,
                         UserDTO creatorDTO,
                         MessageDTO messageDTO,
                         ChatDTO chatDTO) {
        super(id, createdAt);
        this.creatorDTO = creatorDTO;
        this.messageDTO = messageDTO;
        this.chatDTO = chatDTO;
    }

    @Override
    public PinMessageDTO addLinks(User currentUser, boolean doInclude) {
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
