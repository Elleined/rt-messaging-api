package com.elleined.rt_messaging_api.service.poll.option;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.poll.Option;
import com.elleined.rt_messaging_api.model.poll.Poll;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OptionService extends CustomService<Option> {
    Page<Option> getAll(User currentUser,
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

    void removeVote(User currentUser, Option option);

    Optional<Option> getByUser(User currentUser,
                               GroupChat groupChat,
                               Poll poll);

    default boolean isAlreadyVoted(User currentUser, GroupChat groupChat, Poll poll) {
        return poll.getOptions().stream()
                .map(Option::getVotingUsers)
                .flatMap(Collection::stream)
                .anyMatch(currentUser::equals);
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
