package com.elleined.rt_messaging_api.model.chat;

import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "rtmaPrivateChat", usage = CacheConcurrencyStrategy.READ_WRITE)

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
