package com.elleined.rt_messaging_api.service.user;

import com.elleined.rt_messaging_api.model.user.User;
import com.elleined.rt_messaging_api.service.CustomService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService extends CustomService<User> {
    User save(String name,
              String image);

    List<User> getAll(Pageable pageable);
}
