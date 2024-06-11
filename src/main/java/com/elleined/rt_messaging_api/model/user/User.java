package com.elleined.rt_messaging_api.model.user;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.chat.Chat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(
        name = "tbl_user",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User extends PrimaryKeyIdentity {
    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @OneToMany(mappedBy = "creator")
    private List<Chat> chats;
}
