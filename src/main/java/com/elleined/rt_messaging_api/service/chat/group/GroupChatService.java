package com.elleined.rt_messaging_api.service.chat.group;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.ChatService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface GroupChatService extends ChatService<GroupChat> {
    int RECEIVER_LIMIT = 200;

    GroupChat save(User creator,
                   String name,
                   String picture,
                   Set<User> receivers);

    void changeName(User currentUser, GroupChat groupChat, String name);
    void changePicture(User currentUser, GroupChat groupChat, String picture);

    List<User> getAllReceivers(User currentUser, GroupChat groupChat, Pageable pageable);
    void leaveGroup(User currentUser, GroupChat groupChat);

    void addReceiver(User currentUser, GroupChat groupChat, User receiver);

    void removeParticipant(User currentUser, GroupChat groupChat, User participant);

    default void addAllReceiver(User currentUser, GroupChat groupChat, Set<User> receivers) {
        receivers.forEach(receiver -> this.addReceiver(currentUser, groupChat, receiver));
    }

    default boolean isReceiverLimitReached(GroupChat groupChat, User receiver) {
        return receiver != null
                ? groupChat.getReceivers().size() + 1 >= RECEIVER_LIMIT
                : groupChat.getReceivers().size() >= RECEIVER_LIMIT;
    }
}
