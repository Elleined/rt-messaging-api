package com.elleined.rt_messaging_api.dto.chat;

import com.elleined.rt_messaging_api.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PrivateChatDTO extends ChatDTO {
    private int receiverId;
}
