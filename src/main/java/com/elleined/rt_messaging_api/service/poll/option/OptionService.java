package com.elleined.rt_messaging_api.service.poll.option;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.poll.Option;
import com.elleined.rt_messaging_api.model.poll.Poll;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OptionService extends CustomService<Option> {
    List<Option> getAll(User currentUser,
                        GroupChat groupChat,
                        Poll poll,
                        Pageable pageable);

    Option save(User creator,
                GroupChat groupChat,
                Poll poll,
                String option);

    void vote(User currentUser,
              GroupChat groupChat,
              Poll poll,
              Option option);

    Optional<Option> getByUser(User currentUser,
                               GroupChat groupChat,
                               Poll poll,
                               Option option);

    default boolean isAlreadyVoted(Option option, User user) {
        return option.getVotingUsers().contains(user);
    }

    default List<Option> saveAll(User creator,
                                 GroupChat groupChat,
                                 Poll poll,
                                 List<String> options) {

        return options.stream()
                .map(option -> this.save(creator, groupChat, poll, option))
                .toList();
    }
}
