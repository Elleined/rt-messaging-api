package com.elleined.rt_messaging_api.repository.reaction;

import com.elleined.rt_messaging_api.model.reaction.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
}