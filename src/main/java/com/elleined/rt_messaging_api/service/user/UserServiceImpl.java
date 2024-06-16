package com.elleined.rt_messaging_api.service.user;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.mapper.user.UserMapper;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User save(String name, String image) {
        User user = userMapper.toEntity(name, image);

        userRepository.save(user);
        log.debug("Saving user success");
        return user;
    }

    @Override
    public List<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public User getById(int id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<User> getAllById(List<Integer> ids) {
        return userRepository.findAllById(ids);
    }
}
