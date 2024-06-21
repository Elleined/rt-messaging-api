package com.elleined.rt_messaging_api.service.chat.pv;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotOwnedException;
import com.elleined.rt_messaging_api.mapper.chat.PrivateChatMapper;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.chat.PrivateChatRepository;
import com.elleined.rt_messaging_api.repository.user.UserRepository;
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
public class PrivateChatServiceImpl implements PrivateChatService {
    private final UserRepository userRepository;

    private final PrivateChatRepository privateChatRepository;
    private final PrivateChatMapper privateChatMapper;

    @Override
    public PrivateChat save(User creator, User receiver) {
        PrivateChat privateChat = privateChatMapper.toEntity(creator, receiver);

        privateChatRepository.save(privateChat);
        log.debug("Saving private chat success");
        return privateChat;
    }

    @Override
    public void delete(User currentUser, PrivateChat privateChat) {
        if (currentUser.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot delete private chat! because you do not owned this conversation");

        currentUser.getReceivedPrivateChats().remove(privateChat);
        privateChatRepository.delete(privateChat);
        log.debug("Deleting private chat success!");
    }

    @Override
    public boolean hasExistingChat(User creator, User receiver) {
        return creator.getCreatedPrivateChats().stream()
                .map(PrivateChat::getReceiver)
                .anyMatch(receiver::equals) ||

                receiver.getReceivedPrivateChats().stream()
                        .map(PrivateChat::getCreator)
                        .anyMatch(creator::equals);
    }

    @Override
    public PrivateChat getByCreatorAndReceiver(User creator, User receiver) {
        return privateChatRepository.findAll().stream()
                .filter(privateChat ->
                        (privateChat.getCreator().equals(creator) && privateChat.getReceiver().equals(receiver)) ||
                                (privateChat.getCreator().equals(receiver) && privateChat.getReceiver().equals(creator))
                )
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find private chat! This should not be a problem please contact the developer!"));
    }

    @Override
    public List<PrivateChat> getAll(User currentUser, Pageable pageable) {
        return userRepository.findAllPrivateChats(currentUser, pageable).getContent();
    }

    @Override
    public void setNickname(User currentUser, PrivateChat chat, User nicknamedUser, String nickname) {
         if (nicknamedUser.notAllowed(chat))
             throw new ResourceNotOwnedException("Cannot set nickname! because you cannot :) you already know why right?");

        if (currentUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot set nickname! because you cannot :) you already know why right?");

         chat.setNickname(nicknamedUser, nickname);
         privateChatRepository.save(chat);
         log.debug("User with id of {} has now nickname of {}", nicknamedUser.getId(), nickname);
    }

    @Override
    public PrivateChat getById(int id) throws ResourceNotFoundException {
        return privateChatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Private chat with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<PrivateChat> getAllById(List<Integer> ids) {
        return privateChatRepository.findAllById(ids);
    }
}
