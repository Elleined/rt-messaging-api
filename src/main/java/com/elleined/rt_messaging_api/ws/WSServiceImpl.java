package com.elleined.rt_messaging_api.ws;

import com.elleined.rt_messaging_api.dto.mention.MentionDTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WSServiceImpl implements WSService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void broadcast(PrivateChat privateChat, MessageDTO messageDTO) {
        int chatId = privateChat.getId();
        int receiverId = privateChat.getReceiver().getId();

        final String destination = STR."/communication/\{receiverId}/private-chat/\{chatId}/messages";
        simpMessagingTemplate.convertAndSend(destination, messageDTO);
        log.debug("Broadcasting message to private chat success");
    }

    @Override
    public void broadcast(PrivateChat privateChat, Message message, ReactionDTO reactionDTO) {
        int chatId = privateChat.getId();
        int messageId = message.getId();
        int receiverId = privateChat.getReceiver().getId();

        final String destination = STR."/communication/\{receiverId}/private-chat/\{chatId}/messages/\{messageId}/reactions";
        simpMessagingTemplate.convertAndSend(destination, reactionDTO);
        log.debug("Broadcasting reaction to private chat success");
    }

    @Override
    public void broadcast(PrivateChat privateChat, Message message, MentionDTO mentionDTO) {
        int chatId = privateChat.getId();
        int messageId = message.getId();
        int receiverId = privateChat.getReceiver().getId();

        final String destination = STR."/communication/\{receiverId}/private-chat/\{chatId}/messages/\{messageId}/mentions";
        simpMessagingTemplate.convertAndSend(destination, mentionDTO);
        log.debug("Broadcasting mention to private chat success");
    }

    @Override
    public void broadcast(GroupChat groupChat, MessageDTO messageDTO) {
        int chatId = groupChat.getId();
        Set<Integer> receiverIds = groupChat.receiverIds();

        receiverIds.forEach(receiverId -> {
            final String destination = STR."/communication/\{receiverId}/group-chat/\{chatId}/messages";
            simpMessagingTemplate.convertAndSend(destination, messageDTO);
            log.debug("Broadcasting message to group chat success");
        });
    }

    @Override
    public void broadcast(GroupChat groupChat, Message message, ReactionDTO reactionDTO) {
        int chatId = groupChat.getId();
        int messageId = message.getId();
        Set<Integer> receiverIds = groupChat.receiverIds();

        receiverIds.forEach(receiverId -> {
            final String destination = STR."/communication/\{receiverId}/group-chat/\{chatId}/messages/\{messageId}/reactions";
            simpMessagingTemplate.convertAndSend(destination, reactionDTO);
            log.debug("Broadcasting reaction to group chat success");
        });
    }

    @Override
    public void broadcast(GroupChat groupChat, Message message, MentionDTO mentionDTO) {
        int chatId = groupChat.getId();
        int messageId = message.getId();
        Set<Integer> receiverIds = groupChat.receiverIds();

        receiverIds.forEach(receiverId -> {
            final String destination = STR."/communication/\{receiverId}/group-chat/\{chatId}/messages/\{messageId}/mentions";
            simpMessagingTemplate.convertAndSend(destination, mentionDTO);
            log.debug("Broadcasting mention to group chat success");
        });
    }
}
