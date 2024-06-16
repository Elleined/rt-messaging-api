package com.elleined.rt_messaging_api.service.chat.group;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.mapper.chat.GroupChatMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.chat.GroupChatRepository;
import com.elleined.rt_messaging_api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GroupChatServiceImpl implements GroupChatService {
    private final UserRepository userRepository;

    private final GroupChatRepository groupChatRepository;
    private final GroupChatMapper groupChatMapper;

    @Override
    public GroupChat save(User creator, String name, String picture, Set<User> receivers) {
        GroupChat groupChat = groupChatMapper.toEntity(creator, name, picture, receivers);

        groupChatRepository.save(groupChat);
        log.debug("Saving group chat success");
        return groupChat;
    }

    @Override
    public void changeName(User currentUser, GroupChat groupChat, String name) {
        groupChat.setName(name);
        groupChatRepository.save(groupChat);
        log.debug("Changing group name success!");
    }

    @Override
    public void changePicture(User currentUser, GroupChat groupChat, String picture) {
        groupChat.setPicture(picture);
        groupChatRepository.save(groupChat);
        log.debug("Changing picture success!");
    }

    @Override
    public List<User> getAllReceivers(User currentUser, GroupChat groupChat, Pageable pageable) {
        return groupChatRepository.findAllReceivers(groupChat, pageable).getContent();
    }

    @Override
    public void leaveGroup(User currentUser, GroupChat groupChat) {
        groupChat.getReceivers().remove(currentUser);
        currentUser.getGroupChats().remove(groupChat);

        groupChatRepository.save(groupChat);
        userRepository.save(currentUser);

        log.debug("Leaving group chat success");
    }

    @Override
    public void addReceiver(User currentUser, GroupChat groupChat, User receiver) {
        groupChat.getReceivers().add(receiver);
        receiver.getGroupChats().add(groupChat);

        groupChatRepository.save(groupChat);
        userRepository.save(receiver);

        log.debug("Adding receiver success");
    }

    @Override
    public void removeParticipant(User currentUser, GroupChat groupChat, User participant) {
        groupChat.getReceivers().remove(participant);
        participant.getGroupChats().remove(groupChat);

        groupChatRepository.save(groupChat);
        userRepository.save(participant);

        log.debug("Removing participant success");
    }

    @Override
    public List<GroupChat> getAll(User currentUser, Pageable pageable) {
        return userRepository.findAllGroupChats(currentUser, pageable).getContent();
    }

    @Override
    public GroupChat getById(int id) throws ResourceNotFoundException {
        return groupChatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group chat with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<GroupChat> getAllById(List<Integer> ids) {
        return groupChatRepository.findAllById(ids);
    }
}
