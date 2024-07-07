package com.elleined.rt_messaging_api.dto.chat;

import com.elleined.rt_messaging_api.dto.user.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class GroupChatDTO extends ChatDTO {
    private String name;
    private String picture;

    @Builder
    public GroupChatDTO(int id,
                        LocalDateTime createdAt,
                        UserDTO creatorDTO,
                        Map<Integer, String> nicknames,
                        String name,
                        String picture) {
        super(id, createdAt, creatorDTO, nicknames);
        this.name = name;
        this.picture = picture;
    }
}
