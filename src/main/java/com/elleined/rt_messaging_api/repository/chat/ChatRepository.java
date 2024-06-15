package com.elleined.rt_messaging_api.repository.chat;

import com.elleined.rt_messaging_api.model.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository<T extends Chat> extends JpaRepository<T, Integer> {
}