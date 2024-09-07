package com.elleined.rt_messaging_api.service.user;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends CustomService<User> {
    User getByJWT(String jwt) throws ResourceNotFoundException;

    User register(String name,
              String image,
              String email,
              String password);

    String login(String email,
                 String password);

    Page<User> getAll(Pageable pageable);
}
