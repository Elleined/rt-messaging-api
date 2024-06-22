package com.elleined.rt_messaging_api.controller.nickname;

import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.group.GroupChatService;
import com.elleined.rt_messaging_api.service.chat.pv.PrivateChatService;
import com.elleined.rt_messaging_api.service.user.UserService;
import com.elleined.rt_messaging_api.ws.WSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{currentUserId}/private-chats/{privateChatId}/nicknames/{nicknamedUserId}")
@RequiredArgsConstructor
public class PrivateNicknameController {
    private final UserService userService;

    private final PrivateChatService privateChatService;

    private final WSService wsService;

    @PostMapping
    public String setNickname(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("privateChatId") int privateChatId,
                              @PathVariable("nicknamedUserId") int nicknamedUserId,
                              @RequestParam("nickname") String nickname) {

        User currentUser = userService.getById(currentUserId);
        User nicknamedUser = userService.getById(nicknamedUserId);
        PrivateChat privateChat = privateChatService.getById(privateChatId);

        privateChatService.setNickname(currentUser, privateChat, nicknamedUser, nickname);

        String announcement = currentUser == nicknamedUser
                ? STR."\{currentUser.getName()} set his own nickname to: \{nickname}"
                : STR."\{currentUser.getName()} set \{nicknamedUser.getName()} nickname to: \{nickname}";
        wsService.broadcast(privateChat, announcement);
        return nickname;
    }

    @PatchMapping
    public void removeNickname(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("privateChatId") int privateChatId,
                               @PathVariable("nicknamedUserId") int nicknamedUserId) {

        User currentUser = userService.getById(currentUserId);
        User nicknamedUser = userService.getById(nicknamedUserId);
        PrivateChat privateChat = privateChatService.getById(privateChatId);

        privateChatService.removeNickname(currentUser, privateChat, nicknamedUser);

        String announcement = currentUser == nicknamedUser
                ? STR."\{currentUser.getName()} remove his own nickname."
                : STR."\{currentUser.getName()} remove your \{nicknamedUser.getName()}";
        wsService.broadcast(privateChat, announcement);
    }
}
