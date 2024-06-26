package com.elleined.rt_messaging_api.dto.mention;

import com.elleined.rt_messaging_api.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MentionDTO extends DTO {
    private int creatorId;
    private int mentionedUserId;
    private int messageId;
}
