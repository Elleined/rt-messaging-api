package com.elleined.rt_messaging_api.service.chat.pv;

import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.ChatService;

public interface PrivateChatService extends ChatService<PrivateChat> {
    PrivateChat save(User creator,
                     User receiver);

    PrivateChat getOrSave(User currentUser, User receiver);

    void mentionOtherUser(User currentUser, PrivateChat privateChat, Message message);
}
