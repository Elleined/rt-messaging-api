package com.elleined.rt_messaging_api.repository.poll;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.poll.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PollRepository extends JpaRepository<Poll, Integer> {

    @Query("SELECT p FROM Poll p WHERE p.groupChat = :groupChat")
    Page<Poll> findAll(@Param("groupChat") GroupChat groupChat, Pageable pageable);
}