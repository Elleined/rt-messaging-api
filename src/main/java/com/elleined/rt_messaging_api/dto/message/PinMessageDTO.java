package com.elleined.rt_messaging_api.dto.message;

import com.elleined.rt_messaging_api.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PinMessageDTO extends DTO {
    private int creatorId;
    private int messageId;
    private int chatId;
}
