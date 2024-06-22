package com.elleined.rt_messaging_api.controller.nickname;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.group.GroupChatService;
import com.elleined.rt_messaging_api.service.user.UserService;
import com.elleined.rt_messaging_api.ws.WSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{currentUserId}/group-chats/{groupChatId}/nicknames/{nicknamedUserId}")
@RequiredArgsConstructor
public class GroupNicknameController {
    private final UserService userService;

    private final GroupChatService groupChatService;

    private final WSService wsService;

    @PostMapping
    public String setNickname(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("groupChatId") int groupChatId,
                              @PathVariable("nicknamedUserId") int nicknamedUserId,
                              @RequestParam("nickname") String nickname) {

        User currentUser = userService.getById(currentUserId);
        User nicknamedUser = userService.getById(nicknamedUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);

        groupChatService.setNickname(currentUser, groupChat, nicknamedUser, nickname);

        String announcement = currentUser == nicknamedUser
                ? STR."\{currentUser.getName()} set his own nickname to: \{nickname}"
                : STR."\{currentUser.getName()} set \{nicknamedUser.getName()} nickname to: \{nickname}";
        wsService.broadcast(groupChat, announcement);
        return nickname;
    }

    @PatchMapping
    public void removeNickname(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("groupChatId") int groupChatId,
                               @PathVariable("nicknamedUserId") int nicknamedUserId) {

        User currentUser = userService.getById(currentUserId);
        User nicknamedUser = userService.getById(nicknamedUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);

        groupChatService.removeNickname(currentUser, groupChat, nicknamedUser);

        String announcement = currentUser == nicknamedUser
                ? STR."\{currentUser.getName()} remove his own nickname."
                : STR."\{currentUser.getName()} remove your \{nicknamedUser.getName()}";
        wsService.broadcast(groupChat, announcement);
    }
}
