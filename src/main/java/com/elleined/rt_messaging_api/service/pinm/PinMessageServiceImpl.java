package com.elleined.rt_messaging_api.service.pinm;

import com.elleined.rt_messaging_api.exception.resource.ResourceAlreadyExistsException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotOwnedException;
import com.elleined.rt_messaging_api.mapper.message.PinMessageMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.message.PinMessage;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.message.PinMessageRepository;
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
public class PinMessageServiceImpl implements PinMessageService {
    private final PinMessageRepository pinMessageRepository;
    private final PinMessageMapper pinMessageMapper;

    @Override
    public Page<PinMessage> getAll(User currentUser, PrivateChat chat, Pageable pageable) {
        if (currentUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot get all pin messages! because you cannot :) you already know why right?");

        return pinMessageRepository.findAllPinnedMessages(chat, pageable);
    }

    @Override
    public Page<PinMessage> getAll(User currentUser, GroupChat chat, Pageable pageable) {
        if (currentUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot get all pin messages! because you cannot :) you already know why right?");

        return pinMessageRepository.findAllPinnedMessages(chat, pageable);
    }

    @Override
    public PinMessage pin(User currentUser, PrivateChat chat, Message message) {
        if (chat.isPinnedAlready(message))
            throw new ResourceAlreadyExistsException("Cannot pin message! because this message is already been pinned. Either unpin this message");
        if (currentUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot pin message! because you cannot :) you already know why right?");

        if (chat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot pin message! because chat doesn't have this message!");

        PinMessage pinMessage = pinMessageMapper.toEntity(currentUser, message, chat);
        log.debug("Saving pin message success");
        pinMessageRepository.save(pinMessage);
        return pinMessage;
    }

    @Override
    public PinMessage pin(User currentUser, GroupChat chat, Message message) {
        if (chat.isPinnedAlready(message))
            throw new ResourceAlreadyExistsException("Cannot pin message! because this message is already been pinned. Either unpin this message");

        if (currentUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot pin message! because you cannot :) you already know why right?");

        if (chat.notOwned(message))
            throw new ResourceNotOwnedException("Cannot pin message! because chat doesn't have this message!");

        PinMessage pinMessage = pinMessageMapper.toEntity(currentUser, message, chat);
        log.debug("Saving pin message success");
        pinMessageRepository.save(pinMessage);
        return pinMessage;
    }

    @Override
    public void unpin(User currentUser, PrivateChat chat, PinMessage pinMessage) {
        if (currentUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot unpin message! because you cannot :) you already know why right?");

        if (chat.notOwned(pinMessage))
            throw new ResourceNotOwnedException("Cannot unpin message! because chat doesn't have this message!");

        chat.getPinMessages().remove(pinMessage);

        pinMessageRepository.delete(pinMessage);
        log.debug("Deleting pin message success");
    }

    @Override
    public void unpin(User currentUser, GroupChat chat, PinMessage pinMessage) {
        if (currentUser.notAllowed(chat))
            throw new ResourceNotOwnedException("Cannot unpin message! because you cannot :) you already know why right?");

        if (chat.notOwned(pinMessage))
            throw new ResourceNotOwnedException("Cannot unpin message! because chat doesn't have this message!");

        chat.getPinMessages().remove(pinMessage);

        pinMessageRepository.delete(pinMessage);
        log.debug("Deleting pin message success");
    }

    @Override
    public PinMessage getById(int id) throws ResourceNotFoundException {
        return pinMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pin message with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<PinMessage> getAllById(List<Integer> ids) {
        return pinMessageRepository.findAllById(ids);
    }
}
