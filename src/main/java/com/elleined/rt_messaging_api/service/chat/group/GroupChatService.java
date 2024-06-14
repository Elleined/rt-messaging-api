package com.elleined.rt_messaging_api.service.chat.group;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.ChatService;

import java.util.Set;

public interface GroupChatService extends ChatService<GroupChat> {

    GroupChat save(User creator,
                   String name,
                   String picture,
                   Set<User> receivers);


    void addReceiver(GroupChat groupChat, User receiver);
    default void addAllReceiver(GroupChat groupChat, Set<User> receivers) {
        receivers.forEach(receiver -> this.addReceiver(groupChat, receiver));
    }
}
