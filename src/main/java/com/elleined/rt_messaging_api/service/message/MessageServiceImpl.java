package com.elleined.rt_messaging_api.service.message;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotOwnedException;
import com.elleined.rt_messaging_api.mapper.message.MessageMapper;
import com.elleined.rt_messaging_api.model.chat.Chat;
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
    public List<Message> getAllMessage(User currentUser, Chat chat, Pageable pageable) {
        return messageRepository.findAll(chat, pageable).getContent();
    }

    @Override
    public Message save(User creator, String content, Message.ContentType contentType, Chat chat) {
        Message message = messageMapper.toEntity(creator, content, contentType, chat);

        messageRepository.save(message);
        log.debug("Saving message success");
        return message;
    }

    @Override
    public void unsent(User currentUser, Chat chat, Message message) {
        if (currentUser.notOwned(message))
            throw new ResourceNotOwnedException("");

        if (chat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot unsent message! because this chat doesn't have the message");

        chat.getMessages().remove(message);

        messageRepository.delete(message);
        log.debug("Message unsent successfully!");
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
