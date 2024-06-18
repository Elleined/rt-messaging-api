package com.elleined.rt_messaging_api.service.message;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotOwnedException;
import com.elleined.rt_messaging_api.mapper.message.MessageMapper;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<Message> getAllMessage(User currentUser, GroupChat groupChat, Pageable pageable) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot get all messages! because you cannot :) you already know why right?");

        return messageRepository.findAll(groupChat, pageable).stream()
                .filter(Message::isActive)
                .toList();
    }

    @Override
    public List<Message> getAllMessage(User currentUser, PrivateChat privateChat, Pageable pageable) {
        if (currentUser.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot get all messages! because you cannot :) you already know why right?");

        return messageRepository.findAll(privateChat, pageable).stream()
                .filter(Message::isActive)
                .toList();
    }

    @Override
    public Message save(User creator, GroupChat groupChat, String content, Message.ContentType contentType) {
        if (creator.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot send message! because you cannot :) you already know why right?");

        Message message = messageMapper.toEntity(creator, groupChat, content, contentType);

        messageRepository.save(message);
        log.debug("Saving message success");
        return message;
    }

    @Override
    public Message save(User creator, PrivateChat privateChat, String content, Message.ContentType contentType) {
        if (creator.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot send message! because you cannot :) you already know why right?");

        Message message = messageMapper.toEntity(creator, privateChat, content, contentType);

        messageRepository.save(message);
        log.debug("Saving private message success");
        return message;
    }

    @Override
    public void unsent(User currentUser, PrivateChat privateChat, Message message) {
        if (currentUser.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot unsent message! because you cannot :) you already know why right?");

        if (currentUser.notOwned(message))
            throw new ResourceNotOwnedException("Cannot unsent message! because you do not owned this message!");

        if (privateChat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot unsent message! because this chat doesn't have the message!");

        message.setStatus(Message.Status.IN_ACTIVE);

        messageRepository.save(message);
        log.debug("Message unsent successfully!");
    }

    @Override
    public void unsent(User currentUser, GroupChat groupChat, Message message) {

    }

    @Override
    public void removeForYou(User currentUser, Chat chat, Message message) {

    }

    @Override
    public Message getById(int id) throws ResourceNotFoundException {
        return messageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<Message> getAllById(List<Integer> ids) {
        return messageRepository.findAllById(ids);
    }
}
