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

    void changeName(GroupChat groupChat, String name);
    void changePicture(GroupChat groupChat, String picture);

    void leaveGroup(GroupChat groupChat, User currentUser);

    void addReceiver(GroupChat groupChat, User receiver);
    void removeParticipant(User currentUser, GroupChat groupChat, User participant);
    default void addAllReceiver(GroupChat groupChat, Set<User> receivers) {
        receivers.forEach(receiver -> this.addReceiver(groupChat, receiver));
    }
}
