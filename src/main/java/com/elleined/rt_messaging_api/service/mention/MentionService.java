package com.elleined.rt_messaging_api.service.mention;

import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;

import java.util.List;
import java.util.Set;

public interface MentionService extends CustomService<Mention> {
    Mention save(User mentioningUser, User mentionedUser, Message message);
    default List<Mention> saveAll(User mentioningUser, Set<User> mentionedUsers, Message message) {
        if (mentionedUsers.isEmpty()) return null;
        return mentionedUsers.stream()
                .map(mentionedUser -> this.save(mentioningUser, mentionedUser, message))
                .toList();
    }
}
