package com.elleined.rt_messaging_api.service.chat;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;

import java.awt.print.Pageable;
import java.util.List;

public interface ChatService<T extends Chat> extends CustomService<T> {
    List<Message> getAllMessage(T t, Pageable pageable);
    List<T> getAll(User currentUser, Pageable pageable);
}
