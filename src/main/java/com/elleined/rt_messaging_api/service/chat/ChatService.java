package com.elleined.rt_messaging_api.service.chat;

import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService<T extends Chat>
        extends CustomService<T>,
        NicknameService<T> {

    Page<T> getAll(User currentUser, Pageable pageable);
}
