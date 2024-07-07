package com.elleined.rt_messaging_api.service.pinm;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.message.PinMessage;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PinMessageService extends CustomService<PinMessage> {
    Page<PinMessage> getAll(User currentUser, PrivateChat chat, Pageable pageable);
    Page<PinMessage> getAll(User currentUser, GroupChat chat, Pageable pageable);

    PinMessage pin(User currentUser, PrivateChat chat, Message message);
    PinMessage pin(User currentUser, GroupChat chat, Message message);

    void unpin(User currentUser, PrivateChat chat, PinMessage pinMessage);
    void unpin(User currentUser, GroupChat chat, PinMessage pinMessage);
}
