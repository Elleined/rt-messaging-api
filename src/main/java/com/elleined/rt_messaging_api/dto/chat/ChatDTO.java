package com.elleined.rt_messaging_api.dto.chat;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.dto.HateoasDTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ChatDTO extends DTO {
    private UserDTO creatorDTO;
    private Map<Integer, String> nicknames;

    @Builder
    public ChatDTO(int id,
                   LocalDateTime createdAt,
                   UserDTO creatorDTO,
                   Map<Integer, String> nicknames) {
        super(id, createdAt);
        this.creatorDTO = creatorDTO;
        this.nicknames = nicknames;
    }

    @Override
    public ChatDTO addLinks(User currentUser, boolean doInclude) {
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
