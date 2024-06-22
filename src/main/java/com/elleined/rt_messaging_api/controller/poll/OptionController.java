package com.elleined.rt_messaging_api.controller.poll;

import com.elleined.rt_messaging_api.dto.poll.OptionDTO;
import com.elleined.rt_messaging_api.exception.resource.ResourceException;
import com.elleined.rt_messaging_api.mapper.poll.OptionMapper;
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
@RequestMapping("/users/{currentUserId}/group-chats/{groupChatId}/polls/{pollId}/options")
@RequiredArgsConstructor
public class OptionController {
    private final UserService userService;
    private final GroupChatService groupChatService;

    private final PollService pollService;

    private final OptionService optionService;
    private final OptionMapper optionMapper;

    private final WSService wsService;

    @GetMapping
    public List<OptionDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                  @PathVariable("groupChatId") int groupChatId,
                                  @PathVariable("pollId") int pollId,
                                  @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                  @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                  @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                  @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Poll poll = pollService.getById(pollId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return optionService.getAll(currentUser, groupChat, poll, pageable).stream()
                .map(optionMapper::toDTO)
                .toList();
    }

    @PatchMapping("/{optionId}")
    public void vote(@PathVariable("currentUserId") int currentUserId,
                     @PathVariable("groupChatId") int groupChatId,
                     @PathVariable("pollId") int pollId,
                     @PathVariable("optionId") int optionId) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Poll poll = pollService.getById(pollId);
        Option selectedOption = optionService.getById(optionId);

        if (optionService.isAlreadyVoted(currentUser, groupChat, poll)) {
            Option choosenOption = optionService.getByUser(currentUser, groupChat, poll)
                    .orElseThrow(() -> new ResourceException("If you encounter these message please contact the developer!"));

            optionService.removeVote(currentUser, choosenOption);
            optionService.vote(currentUser, groupChat, poll, selectedOption);
            wsService.broadcast(groupChat, STR."\{currentUser.getName()} changed their vote to \{selectedOption.getOption()} in the poll. \{poll.getQuestion()}");
            return;
        }

        optionService.vote(currentUser, groupChat, poll, selectedOption);
        wsService.broadcast(groupChat, STR."\{currentUser.getName()} voted for \{selectedOption.getOption()} in the poll. \{poll.getQuestion()}");
    }

    @PostMapping
    public OptionDTO save(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("groupChatId") int groupChatId,
                       @PathVariable("pollId") int pollId,
                       @RequestParam("option") String option) {

        User currentUser = userService.getById(currentUserId);
        GroupChat groupChat = groupChatService.getById(groupChatId);
        Poll poll = pollService.getById(pollId);

        Option savedOption = optionService.save(currentUser, groupChat, poll, option);

        OptionDTO optionDTO = optionMapper.toDTO(savedOption);

        wsService.broadcast(groupChat, STR."\{currentUser.getName()} added an option to the poll: \{poll.getQuestion()}");
        return optionDTO;
    }
}
