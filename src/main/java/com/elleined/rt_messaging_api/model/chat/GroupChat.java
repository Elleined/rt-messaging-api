package com.elleined.rt_messaging_api.model.chat;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_group_chat")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class GroupChat extends Chat {

    @Column(name = "group_name")
    private String name;

    @Column(name = "group_picture")
    private String picture;

    @ManyToMany
    @JoinTable(
            name = "tbl_group_chat_receiver",
            joinColumns = @JoinColumn(
                    name = "group_chat_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "receiver_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<User> receivers;

    public Set<Integer> receiverIds() {
        return this.getReceivers().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }
}
