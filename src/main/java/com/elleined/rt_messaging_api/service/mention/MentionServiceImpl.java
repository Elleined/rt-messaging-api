package com.elleined.rt_messaging_api.service.mention;

import com.elleined.rt_messaging_api.exception.message.MessageException;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.mapper.mention.MentionMapper;
import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.mention.MentionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MentionServiceImpl implements MentionService {
    private final MentionRepository mentionRepository;
    private final MentionMapper mentionMapper;

    @Override
    public Mention save(User mentioningUser, User mentionedUser, Message message) {
        if (message.isInactive())
            throw new MessageException("Cannot save mention! because message is already been deleted or inactive!");

        Mention mention = mentionMapper.toEntity(mentioningUser, mentionedUser, message);
        mentionRepository.save(mention);
        log.debug("Saving mention success");
        return mention;
    }

    @Override
    public Mention getById(int id) throws ResourceNotFoundException {
        return mentionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<Mention> getAllById(List<Integer> ids) {
        return mentionRepository.findAllById(ids).stream()
                .sorted(Comparator.comparing(PrimaryKeyIdentity::getCreatedAt).reversed())
                .toList();
    }
}
