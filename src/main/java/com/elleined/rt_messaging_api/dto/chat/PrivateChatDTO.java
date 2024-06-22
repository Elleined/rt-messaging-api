package com.elleined.rt_messaging_api.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PrivateChatDTO extends ChatDTO {
    private int receiverId;
}
