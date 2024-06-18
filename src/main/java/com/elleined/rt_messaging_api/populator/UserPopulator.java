package com.elleined.rt_messaging_api.populator;

import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserPopulator implements Populator {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final Faker faker;

    @Override
    public void populate() {
        User user1 = userMapper.toEntity(faker.name().fullName(), faker.avatar().image());
        User user2 = userMapper.toEntity(faker.name().fullName(), faker.avatar().image());
        User user3 = userMapper.toEntity(faker.name().fullName(), faker.avatar().image());

        userRepository.saveAll(List.of(user1, user2, user3));
    }
}
