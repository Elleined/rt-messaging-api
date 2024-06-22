package com.elleined.rt_messaging_api.repository.poll;

import com.elleined.rt_messaging_api.model.poll.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Integer> {
}