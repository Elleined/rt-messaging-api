package com.elleined.rt_messaging_api.controller.chat;

import com.elleined.rt_messaging_api.dto.chat.GroupChatDTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.mapper.chat.GroupChatMapper;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.group.GroupChatService;
import com.elleined.rt_messaging_api.service.user.UserService;
import com.elleined.rt_messaging_api.ws.WSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{currentUserId}/group-chats")
@RequiredArgsConstructor
public class GroupChatController {
    private final UserService userService;
    private final UserMapper userMapper;

    private final GroupChatService groupChatService;
    private final GroupChatMapper groupChatMapper;

    private final WSService wsService;

    @PatchMapping("/{groupChatId}/name")
    public String changeName(@PathVariable("currentUserId") int currentUserId,
                             @PathVariable("groupChatId") int groupChatId,
                             @RequestParam("name") String name) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);

        groupChatService.changeName(currentUser, groupChat, name);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} changed the group name to: \{name}");

        return name;
    }

    @PatchMapping("/{groupChatId}/picture")
    public String changePicture(@PathVariable("currentUserId") int currentUserId,
                                @PathVariable("groupChatId") int groupChatId,
                                @RequestParam("picture") String picture) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);

        groupChatService.changePicture(currentUser, groupChat, picture);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} changed the group photo.");

        return picture;
    }

    @GetMapping("/{groupChatId}/receivers")
    public List<UserDTO> getAllReceivers(@PathVariable("currentUserId") int currentUserId,
                                         @PathVariable("groupChatId") int groupChatId,
                                         @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                         @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                         @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                         @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return groupChatService.getAllReceivers(currentUser, groupChat, pageable).stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @PostMapping("/{groupChatId}/leave")
    public void leaveGroup(@PathVariable("currentUserId") int currentUserId,
                           @PathVariable("groupChatId") int groupChatId) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);

        groupChatService.leaveGroup(currentUser, groupChat);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} leave the group.");
    }

    @PostMapping("/{groupChatId}/receivers/{receiverId}")
    public void addReceiver(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("groupChatId") int groupChatId,
                            @PathVariable("receiverId") int receiverId) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        User receiver = userService.getById(receiverId);

        groupChatService.addReceiver(currentUser, groupChat, receiver);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} added \{receiver.getName()} to the group.");
    }

    @DeleteMapping("/{groupChatId}/remove/{participantId}")
    public void removeParticipant(@PathVariable("currentUserId") int currentUserId,
                                  @PathVariable("groupChatId") int groupChatId,
                                  @PathVariable("participantId") int participantId) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        User participant = userService.getById(participantId);

        groupChatService.removeParticipant(currentUser, groupChat, participant);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} removed \{participant.getName()} to the group.");
    }

    @GetMapping
    public List<GroupChatDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                     @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                     @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                     @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                     @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return groupChatService.getAll(currentUser, pageable).stream()
                .map(groupChatMapper::toDTO)
                .toList();
    }

    @GetMapping("/{groupChatId}")
    public GroupChatDTO getById(@PathVariable("groupChatId") int groupChatId) throws ResourceNotFoundException {
        GroupChat groupChat = groupChatService.getById(groupChatId);
        return groupChatMapper.toDTO(groupChat);
    }

    @GetMapping("/get-all-by-id")
    public List<GroupChatDTO> getAllById(@RequestBody List<Integer> ids) {
        return groupChatService.getAllById(ids).stream()
                .map(groupChatMapper::toDTO)
                .toList();
    }
}
