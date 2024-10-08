package com.elleined.rt_messaging_api.dto.mention;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MentionDTO extends DTO {
    private UserDTO creatorDTO;
    private UserDTO mentionedUserDTO;
    private MessageDTO messageDTO;
}
