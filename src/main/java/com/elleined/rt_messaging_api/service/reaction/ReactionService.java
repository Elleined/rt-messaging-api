package com.elleined.rt_messaging_api.service.reaction;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReactionService extends CustomService<Reaction> {
    Reaction save(User creator,
                  PrivateChat privateChat,
                  Reaction.Emoji emoji,
                  Message message);

    Reaction save(User creator,
                  GroupChat groupChat,
                  Reaction.Emoji emoji,
                  Message message);

    Page<Reaction> getAll(User currentUser, PrivateChat privateChat, Message message, Pageable pageable);
    Page<Reaction> getAll(User currentUser, GroupChat groupChat, Message message, Pageable pageable);

    void update(User currentUser, PrivateChat privateChat, Message message, Reaction reaction, Reaction.Emoji emoji);
    void update(User currentUser, GroupChat groupChat, Message message, Reaction reaction, Reaction.Emoji emoji);

    void delete(User currentUser, PrivateChat privateChat, Message message, Reaction reaction);
    void delete(User currentUser, GroupChat groupChat, Message message, Reaction reaction);

    boolean isAlreadyReactedTo(User currentUser, PrivateChat privateChat, Message message);
    boolean isAlreadyReactedTo(User currentUser, GroupChat groupChat, Message message);

    Reaction getByUser(User currentUser, PrivateChat privateChat, Message message);
    Reaction getByUser(User currentUser, GroupChat groupChat, Message message);

    Page<Reaction> getAllByEmoji(User currentUser, PrivateChat privateChat, Message message, Reaction.Emoji emoji, Pageable pageable);
    Page<Reaction> getAllByEmoji(User currentUser, GroupChat groupChat, Message message, Reaction.Emoji emoji, Pageable pageable);
}
