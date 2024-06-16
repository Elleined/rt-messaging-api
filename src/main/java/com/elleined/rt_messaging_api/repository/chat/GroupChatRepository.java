package com.elleined.rt_messaging_api.repository.chat;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupChatRepository extends ChatRepository<GroupChat> {
    @Query("SELECT gc.receivers FROM GroupChat gc WHERE gc = :groupChat")
    Page<User> findAllReceivers(@Param("groupChat") GroupChat groupChat, Pageable pageable);
}