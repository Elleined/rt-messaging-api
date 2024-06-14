package com.elleined.rt_messaging_api.dto.chat;

import com.elleined.rt_messaging_api.dto.user.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class GroupChatDTO extends ChatDTO {
    private String name;
    private String picture;
    private Set<Integer> receiverIds;
}
