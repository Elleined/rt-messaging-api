package com.elleined.rt_messaging_api.controller.reaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{currentUserId}/chats/{chatId}/messages/{messageId}/reactions")
@RequiredArgsConstructor
public class ReactionController {


    // get by id
    // get all by id
}
