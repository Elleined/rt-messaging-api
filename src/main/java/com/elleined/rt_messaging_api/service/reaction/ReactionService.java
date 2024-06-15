package com.elleined.rt_messaging_api.service.reaction;

import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReactionService extends CustomService<Reaction> {
    Reaction save(User creator,
                  Reaction.Emoji emoji,
                  Message message);
    List<Reaction> getAll(User currentUser, Chat chat, Message message, Pageable pageable);
    void update(User currentUser, Chat chat, Message message, Reaction reaction, Reaction.Emoji emoji);
    void delete(User currentUser, Chat chat, Message message, Reaction reaction);

    boolean isAlreadyReactedTo(User currentUser, Chat chat, Message message);
    Reaction getByUser(User currentUser, Chat chat, Message message);

    default List<Reaction> getAllByEmoji(User currentUser, Chat chat, Message message, Reaction.Emoji emoji, Pageable pageable) {
        return this.getAll(currentUser, chat, message, pageable).stream()
                .filter(reaction -> reaction.getEmoji().equals(emoji))
                .toList();
    }
}
