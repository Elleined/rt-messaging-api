package com.elleined.rt_messaging_api.controller.user;

import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDTO save(@RequestParam("name") String name,
                        @RequestParam("image") String image) {

        User user = userService.save(name, image);
        return userMapper.toDTO(user);
    }

    @GetMapping
    public List<UserDTO> getAll(@RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);
        return userService.getAll(pageable).stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @GetMapping("/{currentUserId}")
    public UserDTO getById(@PathVariable("currentUserId") int currentUserId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        return userMapper.toDTO(currentUser);
    }

    @GetMapping("/get-all-by-id")
    public List<UserDTO> getAllById(@RequestBody List<Integer> ids) {
        return userService.getAllById(ids).stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
