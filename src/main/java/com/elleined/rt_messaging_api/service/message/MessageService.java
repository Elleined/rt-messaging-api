package com.elleined.rt_messaging_api.service.message;

import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;

import java.util.List;

public interface MessageService extends CustomService<Message> {
    Message save(User creator,
                 String content,
                 Message.ContentType contentType,
                 Chat chat,
                 List<Mention> mentions);
}
