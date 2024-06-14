package com.elleined.rt_messaging_api.model.chat;

import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_private_chat")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PrivateChat extends Chat {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "receiver_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User receiver;
}
