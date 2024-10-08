package com.elleined.rt_messaging_api.controller.message;

import com.elleined.rt_messaging_api.dto.mention.MentionDTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.mapper.mention.MentionMapper;
import com.elleined.rt_messaging_api.mapper.message.MessageMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.group.GroupChatService;
import com.elleined.rt_messaging_api.service.mention.MentionService;
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

import java.util.HashSet;
import java.util.List;


@RestController
@RequestMapping("/users/group-chats/{groupChatId}/messages")
@RequiredArgsConstructor
public class GroupMessageController {
    private final UserService userService;

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    private final MentionService mentionService;
    private final MentionMapper mentionMapper;

    private final GroupChatService groupChatService;

    private final WSService wsService;

    @GetMapping
    public Page<MessageDTO> getAllMessage(@RequestHeader("Authorization") String jwt,
                                          @PathVariable("groupChatId") int groupChatId,
                                          @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                          @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                          @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                          @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return messageService.getAllMessage(currentUser, groupChat, pageable)
                .map(messageMapper::toDTO);
    }


    @DeleteMapping("/{messageId}/unsent")
    public void unsent(@RequestHeader("Authorization") String jwt,
                       @PathVariable("groupChatId") int groupChatId,
                       @PathVariable("messageId") int messageId) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Message message = messageService.getById(messageId);

        messageService.unsent(currentUser, groupChat, message);

        MessageDTO messageDTO = messageMapper.toDTO(message);
        wsService.broadcast(groupChat, messageDTO);
        wsService.broadcast(groupChat, STR."\{currentUser.getName()} unsent a message.");
    }

    @PostMapping
    public MessageDTO save(@RequestHeader("Authorization") String jwt,
                           @PathVariable("groupChatId") int groupChatId,
                           @RequestParam("content") String content,
                           @RequestParam("contentType") Message.ContentType contentType,
                           @RequestParam(required = false, name = "mentionUserIds") List<Integer> mentionUserIds) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        String sanitizeContent = HtmlUtils.htmlEscape(content);
        List<User> mentionedUsers = userService.getAllById(mentionUserIds);

        Message message = messageService.save(currentUser, groupChat, sanitizeContent, contentType);
        List<Mention> mentions = mentionService.saveAll(currentUser, new HashSet<>(mentionedUsers), message);

        MessageDTO messageDTO = messageMapper.toDTO(message);
        List<MentionDTO> mentionDTOS = mentions.stream()
                .map(mentionMapper::toDTO)
                .toList();

        wsService.broadcastAll(groupChat, message, mentionDTOS);
        wsService.broadcast(groupChat, messageDTO);
        return messageDTO;
    }
}
