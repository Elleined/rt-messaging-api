package com.elleined.rt_messaging_api.service.reaction;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReactionService extends CustomService<Reaction> {
    Reaction save(User creator,
                  PrivateChat privateChat,
                  Reaction.Emoji emoji,
                  Message message);

    Reaction save(User creator,
                  GroupChat groupChat,
                  Reaction.Emoji emoji,
                  Message message);

    List<Reaction> getAll(User currentUser, PrivateChat privateChat, Message message, Pageable pageable);
    List<Reaction> getAll(User currentUser, GroupChat groupChat, Message message, Pageable pageable);

    void update(User currentUser, PrivateChat privateChat, Message message, Reaction reaction, Reaction.Emoji emoji);
    void update(User currentUser, GroupChat groupChat, Message message, Reaction reaction, Reaction.Emoji emoji);

    void delete(User currentUser, PrivateChat privateChat, Message message, Reaction reaction);
    void delete(User currentUser, GroupChat groupChat, Message message, Reaction reaction);

    boolean isAlreadyReactedTo(User currentUser, PrivateChat privateChat, Message message);
    boolean isAlreadyReactedTo(User currentUser, GroupChat groupChat, Message message);

    Reaction getByUser(User currentUser, PrivateChat privateChat, Message message);
    Reaction getByUser(User currentUser, GroupChat groupChat, Message message);

    default List<Reaction> getAllByEmoji(User currentUser, PrivateChat privateChat, Message message, Reaction.Emoji emoji, Pageable pageable) {
        return this.getAll(currentUser, privateChat, message, pageable).stream()
                .filter(reaction -> reaction.getEmoji().equals(emoji))
                .toList();
    }

    default List<Reaction> getAllByEmoji(User currentUser, GroupChat groupChat, Message message, Reaction.Emoji emoji, Pageable pageable) {
        return this.getAll(currentUser, groupChat, message, pageable).stream()
                .filter(reaction -> reaction.getEmoji().equals(emoji))
                .toList();
    }
}
