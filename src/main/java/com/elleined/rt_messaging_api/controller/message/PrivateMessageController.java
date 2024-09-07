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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PrivateMessageController {
    private final UserService userService;

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    private final PrivateChatService privateChatService;

    private final WSService wsService;

    @GetMapping("/private-chats/{privateChatId}/messages")
    public Page<MessageDTO> getAllMessage(@RequestHeader("Authorization") String jwt,
                                          @PathVariable("privateChatId") int privateChatId,
                                          @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                          @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                          @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                          @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        PrivateChat privateChat = privateChatService.getById(privateChatId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return messageService.getAllMessage(currentUser, privateChat, pageable)
                .map(messageMapper::toDTO);
    }

    @DeleteMapping("/private-chats/{privateChatId}/messages/{messageId}/unsent")
    public void unsent(@RequestHeader("Authorization") String jwt,
                       @PathVariable("privateChatId") int privateChatId,
                       @PathVariable("messageId") int messageId) {

        User currentUser = userService.getByJWT(jwt);
        PrivateChat privateChat = privateChatService.getById(privateChatId);
        Message message = messageService.getById(messageId);

        messageService.unsent(currentUser, privateChat, message);

        MessageDTO messageDTO = messageMapper.toDTO(message);
        wsService.broadcast(privateChat, messageDTO);
        wsService.broadcast(privateChat, STR."\{currentUser.getName()} unsent a message.");
    }

    @PostMapping("/messages/{receiverId}")
    public MessageDTO save(@RequestHeader("Authorization") String jwt,
                           @PathVariable("receiverId") int receiverId,
                           @RequestParam("content") String content,
                           @RequestParam("contentType") Message.ContentType contentType) {

        User currentUser = userService.getByJWT(jwt);
        User receiver = userService.getById(receiverId);
        String sanitizedContent = HtmlUtils.htmlEscape(content);

        if (privateChatService.hasExistingChat(currentUser, receiver) ||
                privateChatService.hasExistingChat(receiver, currentUser)) {

            PrivateChat privateChat = privateChatService.getByCreatorAndReceiver(currentUser, receiver);
            Message message = messageService.save(currentUser, privateChat, sanitizedContent, contentType);
            MessageDTO messageDTO = messageMapper.toDTO(message);
            wsService.broadcast(privateChat, messageDTO);
            return messageDTO;
        }

        PrivateChat privateChat = privateChatService.save(currentUser, receiver);
        Message message = messageService.save(currentUser, privateChat, sanitizedContent, contentType);
        MessageDTO messageDTO = messageMapper.toDTO(message);
        wsService.broadcast(privateChat, messageDTO);
        return messageDTO;
    }
}
