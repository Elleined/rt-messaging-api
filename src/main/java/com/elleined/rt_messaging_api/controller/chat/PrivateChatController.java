package com.elleined.rt_messaging_api.controller.chat;

import com.elleined.rt_messaging_api.dto.chat.PrivateChatDTO;
import com.elleined.rt_messaging_api.mapper.chat.PrivateChatMapper;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.pv.PrivateChatService;
import com.elleined.rt_messaging_api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{currentUserId}/private-chats")
@RequiredArgsConstructor
public class PrivateChatController {
    private final UserService userService;

    private final PrivateChatService privateChatService;
    private final PrivateChatMapper privateChatMapper;

    @GetMapping
    public Page<PrivateChatDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                       @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                       @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                       @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy,
                                       @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return privateChatService.getAll(currentUser, pageable)
                .map(privateChatMapper::toDTO)
                .map(dto -> dto.addLinks(currentUser, includeRelatedLinks));
    }

    @DeleteMapping("/{privateChatId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("privateChatId") int privateChatId) {

        User currentUser = userService.getById(currentUserId);
        PrivateChat privateChat = privateChatService.getById(privateChatId);

        privateChatService.delete(currentUser, privateChat);
    }
}
