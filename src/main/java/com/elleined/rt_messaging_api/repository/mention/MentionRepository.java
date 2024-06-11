package com.elleined.rt_messaging_api.repository.mention;

import com.elleined.rt_messaging_api.model.mention.Mention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Integer> {
}