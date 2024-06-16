package com.elleined.rt_messaging_api.ws;

import com.elleined.rt_messaging_api.dto.mention.MentionDTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WSServiceImpl implements WSService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void broadcast(Chat chat, String announcement) {
        int chatId = chat.getId();

        final String destination = STR."/communications/chat/\{chatId}";
        simpMessagingTemplate.convertAndSend(destination, announcement);
        log.debug("Broadcasting announcement to chat success");
    }

    @Override
    public void broadcast(Chat chat, MessageDTO messageDTO) {
        int chatId = chat.getId();

        final String destination = STR."/communications/chat/\{chatId}/messages";
        simpMessagingTemplate.convertAndSend(destination, messageDTO);
        log.debug("Broadcasting message to chat success");
    }

    @Override
    public void broadcast(Chat chat, Message message, ReactionDTO reactionDTO) {
        int chatId = chat.getId();
        int messageId = message.getId();

        final String destination = STR."/communications/chat/\{chatId}/messages/\{messageId}/reactions";
        simpMessagingTemplate.convertAndSend(destination, reactionDTO);
        log.debug("Broadcasting reaction to chat success");
    }

    @Override
    public void broadcast(Chat chat, Message message, MentionDTO mentionDTO) {
        int chatId = chat.getId();
        int messageId = message.getId();

        final String destination = STR."/communications/chat/\{chatId}/messages/\{messageId}/mentions";
        simpMessagingTemplate.convertAndSend(destination, mentionDTO);
        log.debug("Broadcasting mention to chat success");
    }
}
