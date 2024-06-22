package com.elleined.rt_messaging_api.repository.poll;

import com.elleined.rt_messaging_api.model.poll.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Integer> {
}