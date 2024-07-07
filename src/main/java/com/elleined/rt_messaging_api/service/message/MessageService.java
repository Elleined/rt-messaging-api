package com.elleined.rt_messaging_api.service.message;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService extends CustomService<Message> {
    Page<Message> getAllMessage(User currentUser, GroupChat groupChat, Pageable pageable);
    Page<Message> getAllMessage(User currentUser, PrivateChat privateChat, Pageable pageable);

    Message save(User creator,
                 GroupChat groupChat,
                 String content,
                 Message.ContentType contentType);

    Message save(User creator,
                 PrivateChat privateChat,
                 String content,
                 Message.ContentType contentType);

    void unsent(User currentUser, PrivateChat privateChat, Message message);
    void unsent(User currentUser, GroupChat groupChat, Message message);
}
