package com.elleined.rt_messaging_api.service.poll;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotOwnedException;
import com.elleined.rt_messaging_api.mapper.poll.PollMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.poll.Poll;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.poll.PollRepository;
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
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final PollMapper pollMapper;

    @Override
    public List<Poll> getAll(User currentUser, GroupChat groupChat, Pageable pageable) {
        if (currentUser.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot get all poll! because you cannot :) you already know why right?");

        return pollRepository.findAll(groupChat, pageable).getContent();
    }

    @Override
    public Poll save(User creator, GroupChat groupChat, String question) {
        if (creator.notAllowed(groupChat))
            throw new ResourceNotOwnedException("Cannot save poll! because you cannot :) you already know why right?");

        Poll poll = pollMapper.toEntity(creator, groupChat, question);
        pollRepository.save(poll);
        log.debug("Saving poll success");
        return poll;
    }

    @Override
    public Poll getById(int id) throws ResourceNotFoundException {
        return pollRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Poll with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<Poll> getAllById(List<Integer> ids) {
        return pollRepository.findAllById(ids);
    }
}
