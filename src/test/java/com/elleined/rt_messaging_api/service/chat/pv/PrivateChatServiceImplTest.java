package com.elleined.rt_messaging_api.service.chat.pv;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.elleined.rt_messaging_api.mapper.chat.PrivateChatMapper;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.chat.PrivateChatRepository;
import com.elleined.rt_messaging_api.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class PrivateChatServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PrivateChatRepository privateChatRepository;
    @Mock
    private PrivateChatMapper privateChatMapper;

    @InjectMocks
    private PrivateChatServiceImpl privateChatService;

    @Test
    void save() {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method

        // Behavior Verifications

        // Assertions
    }

    @Test
    void delete() {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method

        // Behavior Verifications

        // Assertions
    }

    @Test
    void hasExistingChat() {
        // Pre defined values

        // Expected Value
        User creator = User.builder()
                .createdPrivateChats(new ArrayList<>())
                .build();

        User receiver = User.builder()
                .receivedPrivateChats(new ArrayList<>())
                .build();

        PrivateChat privateChat = PrivateChat.builder()
                .creator(creator)
                .receiver(receiver)
                .build();

        creator.getCreatedPrivateChats().add(privateChat);
        receiver.getReceivedPrivateChats().add(privateChat);

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        boolean hasExistingChat = privateChatService.hasExistingChat(creator, receiver);

        // Behavior Verifications
        // Assertions
        assertTrue(hasExistingChat);
    }

    @Test
    void getByCreatorAndReceiver() {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method

        // Behavior Verifications

        // Assertions
    }

    @Test
    void getAll() {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method

        // Behavior Verifications

        // Assertions
    }
}