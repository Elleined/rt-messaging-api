package com.elleined.rt_messaging_api.ws;

import com.elleined.rt_messaging_api.dto.mention.MentionDTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.Message;

import java.util.List;

public interface WSService {
    void broadcast(Chat chat, String announcement);
    void broadcast(Chat chat, MessageDTO messageDTO);
    void broadcast(Chat chat, Message message, ReactionDTO reactionDTO);
    void broadcast(Chat chat, Message message, MentionDTO mentionDTO);

    default void broadcastAll(Chat chat, Message message, List<MentionDTO> mentionDTOs) {
        mentionDTOs.forEach(mentionDTO -> broadcast(chat, message, mentionDTO));
    }
}
