package com.elleined.rt_messaging_api.repository.poll;

import com.elleined.rt_messaging_api.model.poll.Option;
import com.elleined.rt_messaging_api.model.poll.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionRepository extends JpaRepository<Option, Integer> {

    @Query("SELECT o FROM Option o WHERE o.poll = :poll")
    Page<Option> findAllByPoll(@Param("poll") Poll poll, Pageable pageable);
}