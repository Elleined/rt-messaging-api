package com.elleined.rt_messaging_api.controller.message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{currentUserId}/chats/{chatId}/messages/{messageId}")
@RequiredArgsConstructor
public class MessageController {


    // get by id
    // get all by id
}
