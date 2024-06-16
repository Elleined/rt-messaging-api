package com.elleined.rt_messaging_api.controller.chat;

import com.elleined.rt_messaging_api.dto.chat.PrivateChatDTO;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.mapper.chat.PrivateChatMapper;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.pv.PrivateChatService;
import com.elleined.rt_messaging_api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{currentUserId}/private-chats")
@RequiredArgsConstructor
public class PrivateChatController {
    private final UserService userService;

    private final PrivateChatService privateChatService;
    private final PrivateChatMapper privateChatMapper;



    @GetMapping
    public List<PrivateChatDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                       @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                       @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                       @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return privateChatService.getAll(currentUser, pageable).stream()
                .map(privateChatMapper::toDTO)
                .toList();
    }

    @GetMapping("/{privateChatId}")
    public PrivateChatDTO getById(@PathVariable("privateChatId") int privateChatId) throws ResourceNotFoundException {
        PrivateChat privateChat = privateChatService.getById(privateChatId);
        return privateChatMapper.toDTO(privateChat);
    }

    @GetMapping("/get-all-by-id")
    public List<PrivateChatDTO> getAllById(@RequestBody List<Integer> ids) {
        return privateChatService.getAllById(ids).stream()
                .map(privateChatMapper::toDTO)
                .toList();
    }
}
