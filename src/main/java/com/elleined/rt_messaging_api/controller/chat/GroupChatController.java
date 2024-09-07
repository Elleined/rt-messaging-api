package com.elleined.rt_messaging_api.controller.chat;

import com.elleined.rt_messaging_api.dto.chat.GroupChatDTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.mapper.chat.GroupChatMapper;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.group.GroupChatService;
import com.elleined.rt_messaging_api.service.user.UserService;
import com.elleined.rt_messaging_api.ws.WSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users/group-chats")
@RequiredArgsConstructor
public class GroupChatController {
    private final UserService userService;
    private final UserMapper userMapper;

    private final GroupChatService groupChatService;
    private final GroupChatMapper groupChatMapper;

    private final WSService wsService;

    @PostMapping
    public GroupChatDTO save(@RequestHeader("Authorization") String jwt,
                             @RequestParam("name") String name,
                             @RequestParam("picture") String picture,
                             @RequestParam("receiverUserIds") Set<Integer> receiverUserIds) {

        User currentUser = userService.getByJWT(jwt);
        List<User> receiverUsers = userService.getAllById(new ArrayList<>(receiverUserIds));

        GroupChat groupChat = groupChatService.save(currentUser, name, picture, new HashSet<>(receiverUsers));
        return groupChatMapper.toDTO(groupChat);
    }

    @PatchMapping("/{groupChatId}/name")
    public String changeName(@RequestHeader("Authorization") String jwt,
                             @PathVariable("groupChatId") int groupChatId,
                             @RequestParam("name") String name) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(groupChatId);

        groupChatService.changeName(currentUser, groupChat, name);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} changed the group name to: \{name}");
        return name;
    }

    @PatchMapping("/{groupChatId}/picture")
    public String changePicture(@RequestHeader("Authorization") String jwt,
                                @PathVariable("groupChatId") int groupChatId,
                                @RequestParam("picture") String picture) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(groupChatId);

        groupChatService.changePicture(currentUser, groupChat, picture);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} changed the group photo.");
        return picture;
    }

    @GetMapping("/{groupChatId}/receivers")
    public Page<UserDTO> getAllReceivers(@RequestHeader("Authorization") String jwt,
                                         @PathVariable("groupChatId") int groupChatId,
                                         @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                         @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                         @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                         @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return groupChatService.getAllReceivers(currentUser, groupChat, pageable)
                .map(userMapper::toDTO);
    }

    @PostMapping("/{groupChatId}/leave")
    public void leaveGroup(@RequestHeader("Authorization") String jwt,
                           @PathVariable("groupChatId") int groupChatId) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(groupChatId);

        groupChatService.leaveGroup(currentUser, groupChat);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} leave the group.");
    }

    @PostMapping("/{groupChatId}/receivers/{receiverId}")
    public void addReceiver(@RequestHeader("Authorization") String jwt,
                            @PathVariable("groupChatId") int groupChatId,
                            @PathVariable("receiverId") int receiverId) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        User receiver = userService.getById(receiverId);

        groupChatService.addReceiver(currentUser, groupChat, receiver);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} added \{receiver.getName()} to the group.");
    }

    @DeleteMapping("/{groupChatId}/receivers/{participantId}")
    public void removeParticipant(@RequestHeader("Authorization") String jwt,
                                  @PathVariable("groupChatId") int groupChatId,
                                  @PathVariable("participantId") int participantId) {

        User currentUser = userService.getByJWT(jwt);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        User participant = userService.getById(participantId);

        groupChatService.removeParticipant(currentUser, groupChat, participant);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} removed \{participant.getName()} to the group.");
    }

    @GetMapping
    public Page<GroupChatDTO> getAll(@RequestHeader("Authorization") String jwt,
                                     @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                     @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                     @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                     @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return groupChatService.getAll(currentUser, pageable)
                .map(groupChatMapper::toDTO);
    }
}
