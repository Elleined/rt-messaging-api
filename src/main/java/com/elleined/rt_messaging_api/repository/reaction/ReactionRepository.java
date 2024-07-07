package com.elleined.rt_messaging_api.repository.reaction;

import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

    @Query("SELECT r FROM Reaction r WHERE r.message = :message")
    Page<Reaction> findAll(@Param("message") Message message, Pageable pageable);

    @Query("""
            SELECT
                r
            FROM
                Reaction r
            WHERE
                r.message = :message
            AND
                r.message.chat = :chat
            AND
                r.emoji = :emoji
            """)
    Page<Reaction> findAllByEmoji(@Param("chat") Chat chat,
                                  @Param("message") Message message,
                                  @Param("emoji") Reaction.Emoji emoji,
                                  Pageable pageable);
}