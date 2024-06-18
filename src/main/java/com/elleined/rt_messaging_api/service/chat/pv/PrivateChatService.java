package com.elleined.rt_messaging_api.service.chat.pv;

import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.ChatService;

import java.util.Optional;

public interface PrivateChatService extends ChatService<PrivateChat> {
    PrivateChat save(User creator,
                     User receiver);

    void delete(User currentUser, PrivateChat privateChat);

    boolean hasExistingChat(User creator,
                            User receiver);

    PrivateChat getByCreatorAndReceiver(User creator,
                                                  User receiver);
}
