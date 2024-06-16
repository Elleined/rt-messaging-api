package com.elleined.rt_messaging_api.ws;

import com.elleined.rt_messaging_api.dto.mention.MentionDTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;

public interface WSService {
    // Private chat
    void broadcast(PrivateChat privateChat, MessageDTO messageDTO);
    void broadcast(PrivateChat privateChat, Message message, ReactionDTO reactionDTO);
    void broadcast(PrivateChat privateChat, Message message, MentionDTO mentionDTO);

    // Group chat
    void broadcast(GroupChat groupChat, MessageDTO messageDTO);
    void broadcast(GroupChat groupChat, Message message, ReactionDTO reactionDTO);
    void broadcast(GroupChat groupChat, Message message, MentionDTO mentionDTO);
}
