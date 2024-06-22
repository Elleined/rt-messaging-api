package com.elleined.rt_messaging_api.controller.poll;

import com.elleined.rt_messaging_api.dto.poll.PollDTO;
import com.elleined.rt_messaging_api.mapper.poll.PollMapper;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.poll.Option;
import com.elleined.rt_messaging_api.model.poll.Poll;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.chat.group.GroupChatService;
import com.elleined.rt_messaging_api.service.poll.PollService;
import com.elleined.rt_messaging_api.service.poll.option.OptionService;
import com.elleined.rt_messaging_api.service.user.UserService;
import com.elleined.rt_messaging_api.ws.WSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{currentUserId}/group-chats/{groupChatId}/polls")
@RequiredArgsConstructor
public class PollController {
    private final UserService userService;
    private final GroupChatService groupChatService;

    private final PollService pollService;
    private final PollMapper pollMapper;

    private final OptionService optionService;

    private final WSService wsService;

    @GetMapping
    public List<PollDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                @PathVariable("groupChatId") int groupChatId,
                                @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return pollService.getAll(currentUser, groupChat, pageable).stream()
                .map(pollMapper::toDTO)
                .toList();
    }

    @PostMapping
    public PollDTO save(@PathVariable("currentUserId") int currentUserId,
                        @PathVariable("groupChatId") int groupChatId,
                        @RequestParam("question") String question,
                        @RequestParam("options") List<String> options) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);

        Poll poll = pollService.save(currentUser, groupChat, question);
        List<Option> savedOptions = optionService.saveAll(currentUser, groupChat, poll, options);

        PollDTO pollDTO = pollMapper.toDTO(poll);
        wsService.broadcast(groupChat, STR."\{currentUser.getName()} created a poll.");
        return pollDTO;
    }
}
