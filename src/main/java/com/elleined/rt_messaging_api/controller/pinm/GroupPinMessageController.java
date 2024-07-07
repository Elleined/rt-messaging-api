package com.elleined.rt_messaging_api.controller.pinm;

import com.elleined.rt_messaging_api.dto.message.PinMessageDTO;
import com.elleined.rt_messaging_api.mapper.message.PinMessageMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.message.PinMessage;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.group.GroupChatService;
import com.elleined.rt_messaging_api.service.message.MessageService;
import com.elleined.rt_messaging_api.service.pinm.PinMessageService;
import com.elleined.rt_messaging_api.service.user.UserService;
import com.elleined.rt_messaging_api.ws.WSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{currentUserId}/group-chats/{groupChatId}/pin-messages")
@RequiredArgsConstructor
public class GroupPinMessageController {
    private final UserService userService;

    private final GroupChatService groupChatService;

    private final PinMessageService pinMessageService;
    private final PinMessageMapper pinMessageMapper;

    private final MessageService messageService;

    private final WSService wsService;

    @GetMapping
    public Page<PinMessageDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                      @PathVariable("groupChatId") int groupChatId,
                                      @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                      @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                      @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                      @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy,
                                      @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return pinMessageService.getAll(currentUser, groupChat, pageable)
                .map(pinMessageMapper::toDTO)
                .map(dto -> dto.addLinks(currentUser, includeRelatedLinks));
    }

    @PostMapping
    public PinMessageDTO pin(@PathVariable("currentUserId") int currentUserId,
                             @PathVariable("groupChatId") int groupChatId,
                             @RequestParam("messageId") int messageId,
                             @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Message message = messageService.getById(messageId);

        PinMessage pinMessage = pinMessageService.pin(currentUser, groupChat, message);
        wsService.broadcast(groupChat, STR."\{currentUser.getName()} pinned a message.");
        return pinMessageMapper.toDTO(pinMessage).addLinks(currentUser, includeRelatedLinks);
    }

    @DeleteMapping("/{pinMessageId}")
    public void unpin(@PathVariable("currentUserId") int currentUserId,
                      @PathVariable("groupChatId") int groupChatId,
                      @PathVariable("pinMessageId") int pinMessageId) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        PinMessage pinMessage = pinMessageService.getById(pinMessageId);

        pinMessageService.unpin(currentUser, groupChat, pinMessage);
        wsService.broadcast(groupChat, STR."\{currentUser.getName()} unpin a message.");
    }
}
