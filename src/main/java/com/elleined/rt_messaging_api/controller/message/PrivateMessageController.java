package com.elleined.rt_messaging_api.controller.message;

import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.mapper.message.MessageMapper;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.pv.PrivateChatService;
import com.elleined.rt_messaging_api.service.message.MessageService;
import com.elleined.rt_messaging_api.service.user.UserService;
import com.elleined.rt_messaging_api.ws.WSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
@RequestMapping("/users/{currentUserId}/messages/{receiverId}")
@RequiredArgsConstructor
public class PrivateMessageController {
    private final UserService userService;

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    private final PrivateChatService privateChatService;

    private final WSService wsService;

    @PostMapping
    public MessageDTO save(@PathVariable("currentUserId") int currentUserId,
                           @RequestParam("content") String content,
                           @RequestParam("contentType") Message.ContentType contentType,
                           @RequestParam("receiverId") int receiverId,
                           @RequestParam("mentionOtherUser") boolean mentionOtherUser) {

        User currentUser = userService.getById(currentUserId);
        User receiver = userService.getById(receiverId);
        PrivateChat privateChat = privateChatService.getOrSave(currentUser, receiver);
        String sanitizeContent = HtmlUtils.htmlEscape(content);

        Message message = messageService.save(currentUser, sanitizeContent, contentType, privateChat);

        if (mentionOtherUser)
            privateChatService.mentionOtherUser(currentUser, privateChat, message);

        MessageDTO messageDTO = messageMapper.toDTO(message);
        wsService.broadcast(privateChat, messageDTO);
        return messageDTO;
    }

}
