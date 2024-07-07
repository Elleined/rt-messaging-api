package com.elleined.rt_messaging_api.service.reaction;

import com.elleined.rt_messaging_api.exception.message.MessageException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotOwnedException;
import com.elleined.rt_messaging_api.mapper.reaction.ReactionMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.message.MessageRepository;
import com.elleined.rt_messaging_api.repository.reaction.ReactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final MessageRepository messageRepository;

    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;

    @Override
    public Reaction save(User creator, PrivateChat privateChat, Reaction.Emoji emoji, Message message) {
        if (creator.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot save reaction! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot save reaction! because message is already been deleted or inactive!");

        Reaction reaction = reactionMapper.toEntity(creator, emoji, message);

        reactionRepository.save(reaction);
        log.debug("Saving reaction success");
        return reaction;
    }

    @Override
    public Reaction save(User creator, GroupChat groupChat, Reaction.Emoji emoji, Message message) {
        if (creator.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot save reaction! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot save reaction! because message is already been deleted or inactive!");

        Reaction reaction = reactionMapper.toEntity(creator, emoji, message);

        reactionRepository.save(reaction);
        log.debug("Saving reaction success");
        return reaction;
    }

    @Override
    public Page<Reaction> getAll(User currentUser, PrivateChat privateChat, Message message, Pageable pageable) {
        if (currentUser.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot get all reaction! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot get all reaction! because message is already been deleted or inactive!");

        if (privateChat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot get all reaction! because chat doesn't have this message!");

        return reactionRepository.findAll(message, pageable);
    }

    @Override
    public Page<Reaction> getAll(User currentUser, GroupChat groupChat, Message message, Pageable pageable) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot get all reaction! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot get all reaction! because message is already been deleted or inactive!");

        if (groupChat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot get all reaction! because chat doesn't have this message!");

        return reactionRepository.findAll(message, pageable);
    }

    @Override
    public void update(User currentUser, PrivateChat privateChat, Message message, Reaction reaction, Reaction.Emoji emoji) {
        if (currentUser.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot update reaction! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot update reaction! because message is already been deleted or inactive!");

        if (privateChat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot update reaction! because chat doesn't have this message!");

        if (message.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot update reaction! because message doesn't have this reaction!");

        if (currentUser.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot update reaction! because you do not own this reaction!");

        reaction.setEmoji(emoji);
        reactionRepository.save(reaction);

        log.debug("Updating reaction with id of {} success with new emoji of {}", reaction.getId(), emoji);
    }

    @Override
    public void update(User currentUser, GroupChat groupChat, Message message, Reaction reaction, Reaction.Emoji emoji) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot update reaction! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot update reaction! because message is already been deleted or inactive!");

        if (groupChat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot update reaction! because chat doesn't have this message!");

        if (message.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot update reaction! because message doesn't have this reaction!");

        if (currentUser.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot update reaction! because you do not own this reaction!");

        reaction.setEmoji(emoji);
        reactionRepository.save(reaction);

        log.debug("Updating reaction with id of {} success with new emoji of {}", reaction.getId(), emoji);
    }

    @Override
    public void delete(User currentUser, PrivateChat privateChat, Message message, Reaction reaction) {
        if (currentUser.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot delete reaction! because you cannot :) you already know why right?");

        if (currentUser.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot delete reaction! because you do not own this reaction!");

        if (message.isInactive())
            throw new MessageException("Cannot delete reaction! because message is already been deleted or inactive!");

        if (privateChat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot delete reaction! because chat doesn't have this message!");

        if (message.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot delete reaction! because message doesn't have this reaction!");

        message.getReactions().remove(reaction);

        messageRepository.save(message);
        reactionRepository.delete(reaction);
        log.debug("Deleting reaction to message with id of {} success", message.getId());
    }

    @Override
    public void delete(User currentUser, GroupChat groupChat, Message message, Reaction reaction) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot delete reaction! because you cannot :) you already know why right?");

        if (currentUser.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot delete reaction! because you do not own this reaction!");

        if (message.isInactive())
            throw new MessageException("Cannot delete reaction! because message is already been deleted or inactive!");

        if (groupChat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot delete reaction! because chat doesn't have this message!");

        if (message.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot delete reaction! because message doesn't have this reaction!");


        message.getReactions().remove(reaction);

        messageRepository.save(message);
        reactionRepository.delete(reaction);
        log.debug("Deleting reaction to message with id of {} success", message.getId());
    }

    @Override
    public boolean isAlreadyReactedTo(User currentUser, PrivateChat privateChat, Message message) {
        if (currentUser.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot get is already reacted to message! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot get is already reacted to message! because message is already been deleted or inactive!");

        return message.getReactions().stream()
                .map(Reaction::getCreator)
                .anyMatch(currentUser::equals);
    }

    @Override
    public boolean isAlreadyReactedTo(User currentUser, GroupChat groupChat, Message message) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot get is already reacted to message! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot get is already reacted to message! because message is already been deleted or inactive!");

        return message.getReactions().stream()
                .map(Reaction::getCreator)
                .anyMatch(currentUser::equals);
    }

    @Override
    public Reaction getByUser(User currentUser, PrivateChat privateChat, Message message) {
        if (currentUser.notAllowed(privateChat))
            throw new ResourceNotOwnedException("Cannot get reaction by user! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot get reaction by user! because message is already been deleted or inactive!");

        return message.getReactions().stream()
                .filter(reaction -> reaction.getCreator().equals(currentUser))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Getting reaction by user failed! "));
    }

    @Override
    public Reaction getByUser(User currentUser, GroupChat groupChat, Message message) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot get reaction by user! because you cannot :) you already know why right?");

        if (message.isInactive())
            throw new MessageException("Cannot get reaction by user! because message is already been deleted or inactive!");

        return message.getReactions().stream()
                .filter(reaction -> reaction.getCreator().equals(currentUser))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Getting reaction by user failed! "));
    }

    @Override
    public Page<Reaction> getAllByEmoji(User currentUser, PrivateChat privateChat, Message message, Reaction.Emoji emoji, Pageable pageable) {
        return reactionRepository.findAllByEmoji(currentUser, privateChat, message, emoji, pageable);
    }

    @Override
    public Page<Reaction> getAllByEmoji(User currentUser, GroupChat groupChat, Message message, Reaction.Emoji emoji, Pageable pageable) {
        return reactionRepository.findAllByEmoji(currentUser, groupChat, message, emoji, pageable);
    }

    @Override
    public Reaction getById(int id) throws ResourceNotFoundException {
        return reactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction with id of " + id + " doesn't exists"));
    }

    @Override
    public List<Reaction> getAllById(List<Integer> ids) {
        return reactionRepository.findAllById(ids);
    }
}
