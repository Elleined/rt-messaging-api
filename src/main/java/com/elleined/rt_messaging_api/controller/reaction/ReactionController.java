package com.elleined.rt_messaging_api.controller.reaction;

import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.mapper.reaction.ReactionMapper;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import com.elleined.rt_messaging_api.service.reaction.ReactionService;
import com.elleined.rt_messaging_api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{currentUserId}/chats/{chatId}/messages/{messageId}/reactions")
@RequiredArgsConstructor
public class ReactionController {
    private final UserService userService;

    private final ReactionService reactionService;
    private final ReactionMapper reactionMapper;

    @GetMapping("/{reactionId}")
    public ReactionDTO getById(@PathVariable("reactionId") int reactionId) throws ResourceNotFoundException {
        Reaction reaction = reactionService.getById(reactionId);
        return reactionMapper.toDTO(reaction);
    }

    @GetMapping("/get-all-by-id")
    public List<ReactionDTO> getAllById(@RequestBody List<Integer> ids) {
        return reactionService.getAllById(ids).stream()
                .map(reactionMapper::toDTO)
                .toList();
    }
}
