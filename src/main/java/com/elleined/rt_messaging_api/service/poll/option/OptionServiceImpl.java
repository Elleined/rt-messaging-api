package com.elleined.rt_messaging_api.service.poll.option;

import com.elleined.rt_messaging_api.exception.resource.ResourceAlreadyExistsException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotOwnedException;
import com.elleined.rt_messaging_api.mapper.poll.OptionMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.poll.Option;
import com.elleined.rt_messaging_api.model.poll.Poll;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.poll.OptionRepository;
import com.elleined.rt_messaging_api.repository.poll.PollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
    private final PollRepository pollRepository;

    private final OptionRepository optionRepository;
    private final OptionMapper optionMapper;

    @Override
    public List<Option> getAll(User currentUser, GroupChat groupChat, Poll poll, Pageable pageable) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot get all option! because you cannot :) you already know why right?");

        if (groupChat.notOwned(poll))
            throw new ResourceNotOwnedException("Cannot get all option! because group chat doesn't have this poll!");

        return optionRepository.findAllByPoll(poll, pageable).getContent();
    }

    @Override
    public Option save(User creator, GroupChat groupChat, Poll poll, String option) {
        if (creator.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot save option! because you cannot :) you already know why right?");

        if (groupChat.notOwned(poll))
            throw new ResourceNotOwnedException("Cannot save option! because group chat doesn't have this poll!");

        Option savedOption = optionMapper.toEntity(creator, poll, option);
        poll.getOptions().add(savedOption);

        optionRepository.save(savedOption);
        pollRepository.save(poll);
        log.debug("Saving option success");
        return savedOption;
    }

    @Override
    public void vote(User currentUser, GroupChat groupChat, Poll poll, Option option) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot vote to this option! because you cannot :) you already know why right?");

        if (groupChat.notOwned(poll))
            throw new ResourceNotOwnedException("Cannot vote to this option! because group chat doesn't have this poll!");

        if (poll.notOwned(option))
            throw new ResourceNotOwnedException("Cannot vote to this option! because poll doesn't have this option!");

        option.getVotingUsers().add(currentUser);
        optionRepository.save(option);
        log.debug("Current user with id of {} voted poll with id of {} in its option with id of {}", currentUser.getId(), poll.getId(), option.getId());
    }

    @Override
    public void removeVote(User currentUser, Option option) {
        option.getVotingUsers().remove(currentUser);
        optionRepository.save(option);
        log.debug("Removing the old vote success");
    }

    @Override
    public Optional<Option> getByUser(User currentUser, GroupChat groupChat, Poll poll) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot get by user! because you cannot :) you already know why right?");

        if (groupChat.notOwned(poll))
            throw new ResourceNotOwnedException("Cannot get by user! because group chat doesn't have this poll!");

        return poll.getOptions().stream()
                .filter(pollOptions -> pollOptions.getVotingUsers().contains(currentUser))
                .findFirst();
    }

    @Override
    public Option getById(int id) throws ResourceNotFoundException {
        return optionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Option with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<Option> getAllById(List<Integer> ids) {
        return optionRepository.findAllById(ids);
    }
}
