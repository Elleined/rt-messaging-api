package com.elleined.rt_messaging_api.service.poll;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.poll.Poll;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PollService extends CustomService<Poll> {
    Page<Poll> getAll(User currentUser, GroupChat groupChat, Pageable pageable);

    Poll save(User creator,
              GroupChat groupChat,
              String question);
}
