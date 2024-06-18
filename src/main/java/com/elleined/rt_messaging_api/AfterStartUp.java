package com.elleined.rt_messaging_api;

import com.elleined.rt_messaging_api.populator.UserPopulator;
import com.elleined.rt_messaging_api.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AfterStartUp {

    private final UserPopulator userPopulator;

    private final UserRepository userRepository;

    @PostConstruct
    void init() {
        if (userRepository.existsById(1))
            return;

        System.out.println("Please wait populating user table...");
        userPopulator.populate();
        System.out.println("Populating user table success...");
    }
}
