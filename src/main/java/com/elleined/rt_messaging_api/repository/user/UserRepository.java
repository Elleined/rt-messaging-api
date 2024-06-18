package com.elleined.rt_messaging_api.repository.user;

import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u.receivedPrivateChats FROM User u WHERE u = :currentUser")
    Page<PrivateChat> findAllPrivateChats(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.receivedGroupChats FROM User u WHERE u = :currentUser")
    Page<GroupChat> findAllGroupChats(@Param("currentUser") User currentUser, Pageable pageable);
}