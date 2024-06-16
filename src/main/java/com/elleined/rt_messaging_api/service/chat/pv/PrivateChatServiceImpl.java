package com.elleined.rt_messaging_api.service.chat.pv;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
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
    public PrivateChat getOrSave(User currentUser, User receiver) {
        // Checks if Current user as creator doesn't have the receiver to its created private chats.
        // Here Receiver acts as the receiver itself
        boolean createDecision1 = currentUser.getCreatedPrivateChats().stream()
                .map(PrivateChat::getReceiver)
                .noneMatch(receiver::equals);

        // Check if Current user as Receiver doesn't have the creator to its received private chats
        // Here receiver acts as the Creator
        boolean createDecision2 = currentUser.getReceivedPrivateChats().stream()
                .map(PrivateChat::getCreator)
                .noneMatch(receiver::equals);

        if (createDecision1 && createDecision2)
            return this.save(currentUser, receiver);

        // Checks if Current user as creator have the receiver to its created private chats.
        // Here Receiver acts as the receiver itself
        boolean getDecision1 = currentUser.getCreatedPrivateChats().stream()
                .map(PrivateChat::getReceiver)
                .anyMatch(receiver::equals);

        // Check if Current user as Receiver have the creator to its received private chats
        // Here receiver acts as the Creator
        boolean getDecision2 = currentUser.getReceivedPrivateChats().stream()
                .map(PrivateChat::getCreator)
                .anyMatch(receiver::equals);

        return null;
    }

    @Override
    public List<PrivateChat> getAll(User currentUser, Pageable pageable) {
        return userRepository.findAllPrivateChats(currentUser, pageable).getContent();
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
