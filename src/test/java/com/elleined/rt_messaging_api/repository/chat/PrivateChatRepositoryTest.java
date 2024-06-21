package com.elleined.rt_messaging_api.repository.chat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.elleined.rt_messaging_api.mapper.chat.PrivateChatMapper;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.user.UserRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test_application.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PrivateChatRepositoryTest {

    @Autowired
    private PrivateChatRepository privateChatRepository;

    @Autowired
    private UserRepository userRepository;

    private PrivateChatMapper privateChatMapper = Mappers.getMapper(PrivateChatMapper.class);
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    @Test
    void setNickname() {
        Faker faker = new Faker();

        User creator = userMapper.toEntity(faker.name().fullName(), faker.avatar().image());
        User receiver = userMapper.toEntity(faker.name().fullName(), faker.avatar().image());

        PrivateChat privateChat = privateChatMapper.toEntity(creator, receiver);

        privateChat.getNicknames().put(creator, faker.naruto().character());
        privateChat.getNicknames().put(receiver, faker.naruto().character());

        userRepository.save(creator);
        userRepository.save(receiver);
        privateChatRepository.save(privateChat);
    }
}