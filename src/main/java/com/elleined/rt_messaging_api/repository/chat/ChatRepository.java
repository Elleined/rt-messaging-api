package com.elleined.rt_messaging_api.repository.chat;

import com.elleined.rt_messaging_api.model.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}