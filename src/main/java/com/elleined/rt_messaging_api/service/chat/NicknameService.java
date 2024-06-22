package com.elleined.rt_messaging_api.service.chat;

import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.user.User;

public interface NicknameService<T extends Chat> {
    void setNickname(User currentUser, T chat, User nicknamedUser, String nickname);
    void removeNickname(User currentUser, T chat, User nicknamedUser);
}
