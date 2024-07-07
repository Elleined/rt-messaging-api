package com.elleined.rt_messaging_api.service.chat.group;

import com.elleined.rt_messaging_api.exception.chat.GroupChatException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotOwnedException;
import com.elleined.rt_messaging_api.mapper.chat.GroupChatMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.chat.GroupChatRepository;
import com.elleined.rt_messaging_api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
        receivers.add(creator);
        GroupChat groupChat = groupChatMapper.toEntity(creator, name, picture, receivers);

        groupChatRepository.save(groupChat);
        log.debug("Saving group chat success");
        return groupChat;
    }

    @Override
    public void changeName(User currentUser, GroupChat groupChat, String name) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot change group name! because you cannot :) you already know why right?");

        groupChat.setName(name);
        groupChatRepository.save(groupChat);
        log.debug("Changing group name success!");
    }

    @Override
    public void changePicture(User currentUser, GroupChat groupChat, String picture) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot change group picture! because you cannot :) you already know why right?");

        groupChat.setPicture(picture);
        groupChatRepository.save(groupChat);
        log.debug("Changing picture success!");
    }

    @Override
    public Page<User> getAllReceivers(User currentUser, GroupChat groupChat, Pageable pageable) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot get all receiver! because you cannot :) you already know why right?");

        return groupChatRepository.findAllReceivers(groupChat, pageable);
    }

    @Override
    public void leaveGroup(User currentUser, GroupChat groupChat) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot leave group! because you cannot :) you already know why right?");

        groupChat.getReceivers().remove(currentUser);
        currentUser.getReceivedGroupChats().remove(groupChat);

        groupChatRepository.save(groupChat);
        userRepository.save(currentUser);

        log.debug("Leaving group chat success");
    }

    @Override
    public void addReceiver(User currentUser, GroupChat groupChat, User receiver) {
        if (groupChat.hasReceiver(receiver))
            throw new GroupChatException("Cannot add participant! because this receiver already been added!");

        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot add participant! because you cannot :) you already know why right?");

        if (isReceiverLimitReached(groupChat, receiver))
            throw new GroupChatException("Cannot add participant! because this group chat already reached the receiver limit which is " + RECEIVER_LIMIT);

        groupChat.getReceivers().add(receiver);
        receiver.getReceivedGroupChats().add(groupChat);

        groupChatRepository.save(groupChat);
        userRepository.save(receiver);

        log.debug("Adding receiver success");
    }

    @Override
    public void removeParticipant(User currentUser, GroupChat groupChat, User participant) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot remove participant! because you cannot :) you already know why right?");

        groupChat.getReceivers().remove(participant);
        participant.getReceivedGroupChats().remove(groupChat);

        groupChatRepository.save(groupChat);
        userRepository.save(participant);

        log.debug("Removing participant success");
    }

    @Override
    public Page<GroupChat> getAll(User currentUser, Pageable pageable) {
        return userRepository.findAllGroupChats(currentUser, pageable);
    }

    @Override
    public void setNickname(User currentUser, GroupChat chat, User nicknamedUser, String nickname) {
        if (nicknamedUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot set nickname! because you cannot :) you already know why right?");

        if (currentUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot set nickname! because you cannot :) you already know why right?");

        chat.getNicknames().put(nicknamedUser, nickname);
        groupChatRepository.save(chat);
        log.debug("User with id of {} has now nickname of {}", nicknamedUser.getId(), nickname);
    }

    @Override
    public void removeNickname(User currentUser, GroupChat chat, User nicknamedUser) {
        if (nicknamedUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot set nickname! because you cannot :) you already know why right?");

        if (currentUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot set nickname! because you cannot :) you already know why right?");

        chat.getNicknames().remove(nicknamedUser);
        groupChatRepository.save(chat);
        log.debug("User with id of {} nickname has been removed", nicknamedUser.getId());
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
