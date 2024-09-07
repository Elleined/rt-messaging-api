package com.elleined.rt_messaging_api.controller.reaction;

import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import com.elleined.rt_messaging_api.mapper.reaction.ReactionMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.group.GroupChatService;
import com.elleined.rt_messaging_api.service.message.MessageService;
import com.elleined.rt_messaging_api.service.reaction.ReactionService;
import com.elleined.rt_messaging_api.service.user.UserService;
import com.elleined.rt_messaging_api.ws.WSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/group-chats/{chatId}/messages/{messageId}/reactions")
@RequiredArgsConstructor
public class GroupMessageReactionController {
    private final UserService userService;

    private final GroupChatService groupChatService;

    private final MessageService messageService;

    private final ReactionService reactionService;
    private final ReactionMapper reactionMapper;

    private final WSService wsService;

    @PostMapping
    public ReactionDTO save(@RequestHeader("Authorization") String jwt,
                            @PathVariable("chatId") int chatId,
                            @PathVariable("messageId") int messageId,
                            @RequestParam("emoji") Reaction.Emoji emoji) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(chatId);
        Message message = messageService.getById(messageId);

        if (reactionService.isAlreadyReactedTo(currentUser, groupChat, message)) {
            Reaction reaction = reactionService.getByUser(currentUser, groupChat, message);
            reactionService.update(currentUser, groupChat, message, reaction, emoji);
            return reactionMapper.toDTO(reaction);
        }

        Reaction reaction = reactionService.save(currentUser, groupChat, emoji, message);
        ReactionDTO reactionDTO = reactionMapper.toDTO(reaction);
        wsService.broadcast(groupChat, message, reactionDTO);
        return reactionDTO;
    }

    @GetMapping
    public Page<ReactionDTO> getAll(@RequestHeader("Authorization") String jwt,
                                    @PathVariable("chatId") int chatId,
                                    @PathVariable("messageId") int messageId,
                                    @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                    @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                    @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                    @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(chatId);
        Message message = messageService.getById(messageId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAll(currentUser, groupChat, message, pageable)
                .map(reactionMapper::toDTO);
    }

    @GetMapping("/emoji")
    public Page<ReactionDTO> getAllByEmoji(@RequestHeader("Authorization") String jwt,
                                           @PathVariable("chatId") int chatId,
                                           @PathVariable("messageId") int messageId,
                                           @RequestParam("emoji") Reaction.Emoji emoji,
                                           @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                           @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                           @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                           @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(chatId);
        Message message = messageService.getById(messageId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAllByEmoji(currentUser, groupChat, message, emoji, pageable)
                .map(reactionMapper::toDTO);
    }

    @DeleteMapping("/{reactionId}")
    public void delete(@RequestHeader("Authorization") String jwt,
                       @PathVariable("chatId") int chatId,
                       @PathVariable("messageId") int messageId,
                       @PathVariable("reactionId") int reactionId) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(chatId);
        Message message = messageService.getById(messageId);
        Reaction reaction = reactionService.getById(reactionId);

        reactionService.delete(currentUser, groupChat, message, reaction);
    }
}
