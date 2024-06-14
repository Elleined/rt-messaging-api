package com.elleined.rt_messaging_api.dto.message;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.dto.chat.ChatDTO;
import com.elleined.rt_messaging_api.dto.mention.MentionDTO;
import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.model.message.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MessageDTO extends DTO {
    private int creatorId;
    private String content;
    private Message.ContentType contentType;
    private int chatId;
    private List<Integer> reactionIds;
    private List<Integer> mentionIds;
}
