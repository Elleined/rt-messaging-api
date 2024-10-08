package com.elleined.rt_messaging_api.dto.reaction;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ReactionDTO extends DTO {
    private UserDTO creatorDTO;
    private Reaction.Emoji emoji;
    private MessageDTO messageDTO;
}
