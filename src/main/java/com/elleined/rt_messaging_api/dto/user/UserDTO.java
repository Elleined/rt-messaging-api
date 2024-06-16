package com.elleined.rt_messaging_api.dto.user;

import com.elleined.rt_messaging_api.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDTO extends DTO {
    private String name;
    private String image;
    private List<Integer> privateChatIds;
    private Set<Integer> groupChatIds;
}
