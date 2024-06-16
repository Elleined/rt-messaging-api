package com.elleined.rt_messaging_api.dto.reaction;

import com.elleined.rt_messaging_api.dto.DTO;
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
    private int creatorId;
    private Reaction.Emoji emoji;
    private int messageId;
}
