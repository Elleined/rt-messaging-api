package com.elleined.rt_messaging_api.controller.mention;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{currentUserId}/chats/{chatId}/messages/{messageId}/mentions")
@RequiredArgsConstructor
public class MentionController {


    // get by id
    // get all by id
}
