package com.elleined.rt_messaging_api.repository.message;

import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.PinMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PinMessageRepository extends JpaRepository<PinMessage, Integer> {

    @Query("SELECT pm FROM PinMessage pm WHERE pm.chat = :chat")
    Page<PinMessage> findAllPinnedMessages(@Param("chat") Chat chat, Pageable pageable);
}