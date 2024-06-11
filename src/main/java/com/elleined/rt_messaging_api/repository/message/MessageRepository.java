package com.elleined.rt_messaging_api.repository.message;

import com.elleined.rt_messaging_api.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}