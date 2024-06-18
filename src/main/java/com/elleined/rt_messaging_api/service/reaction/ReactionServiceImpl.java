package com.elleined.rt_messaging_api.service.reaction;

import com.elleined.rt_messaging_api.exception.message.MessageException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotOwnedException;
import com.elleined.rt_messaging_api.mapper.reaction.ReactionMapper;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.message.MessageRepository;
import com.elleined.rt_messaging_api.repository.reaction.ReactionRepository;
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
public class ReactionServiceImpl implements ReactionService {
    private final MessageRepository messageRepository;

    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;

    @Override
    public Reaction save(User creator, Reaction.Emoji emoji, Message message) {
        if (message.isInactive())
            throw new MessageException("Cannot save reaction! because message is already been deleted or inactive!");

        Reaction reaction = reactionMapper.toEntity(creator, emoji, message);

        reactionRepository.save(reaction);
        log.debug("Saving reaction success");
        return reaction;
    }

    @Override
    public List<Reaction> getAll(User currentUser, Chat chat, Message message, Pageable pageable) {
        if (message.isInactive())
            throw new MessageException("Cannot get all reaction! because message is already been deleted or inactive!");

        if (chat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot get all reaction! because chat doesn't have this message!");

        return reactionRepository.findAll(message, pageable).getContent();
    }

    @Override
    public void update(User currentUser, Chat chat, Message message, Reaction reaction, Reaction.Emoji emoji) {
        if (message.isInactive())
            throw new MessageException("Cannot update reaction! because message is already been deleted or inactive!");

        if (chat.notOwned(message))
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
    public void delete(User currentUser, Chat chat, Message message, Reaction reaction) {
        if (message.isInactive())
            throw new MessageException("Cannot delete reaction! because message is already been deleted or inactive!");

        if (chat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot delete reaction! because chat doesn't have this message!");

        if (message.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot delete reaction! because message doesn't have this reaction!");

        if (currentUser.notOwned(reaction))
            throw new ResourceNotOwnedException("Cannot delete reaction! because you do not own this reaction!");

        message.getReactions().remove(reaction);

        messageRepository.save(message);
        reactionRepository.delete(reaction);
        log.debug("Deleting reaction to message with id of {} success", message.getId());
    }

    @Override
    public boolean isAlreadyReactedTo(User currentUser, Chat chat, Message message) {
        if (message.isInactive())
            throw new MessageException("Cannot get is already reacted to reaction! because message is already been deleted or inactive!");

        return message.getReactions().stream()
                .map(Reaction::getCreator)
                .anyMatch(currentUser::equals);
    }

    @Override
    public Reaction getByUser(User currentUser, Chat chat, Message message) {
        if (message.isInactive())
            throw new MessageException("Cannot get reaction by user! because message is already been deleted or inactive!");

        return message.getReactions().stream()
                .filter(reaction -> reaction.getCreator().equals(currentUser))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Getting reaction by user failed! "));
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
