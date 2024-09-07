package com.elleined.rt_messaging_api.model.user;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Cacheable
@org.hibernate.annotations.Cache(region = "rtmaUser", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(
        name = "tbl_user",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User extends PrimaryKeyIdentity implements UserDetails {

    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(name = "password")
    private String password;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "receiver")
    private List<PrivateChat> createdPrivateChats;

    @OneToMany(mappedBy = "receiver")
    private List<PrivateChat> receivedPrivateChats;

    @ManyToMany(mappedBy = "receivers")
    private Set<GroupChat> receivedGroupChats;

    @OneToMany(mappedBy = "creator")
    private List<Message> messages;

    @OneToMany(mappedBy = "creator")
    private List<Reaction> reactions;

    @OneToMany(mappedBy = "mentionedUser")
    private List<Mention> receivedMentions;

    public boolean notOwned(Reaction reaction) {
        return !this.getReactions().contains(reaction);
    }

    public boolean notOwned(Message message) {
        return !this.getMessages().contains(message);
    }

    public boolean notAllowed(PrivateChat privateChat) {
        return !privateChat.getCreator().equals(this) &&
                !privateChat.getReceiver().equals(this);
    }

    public boolean notAllowed(GroupChat groupChat) {
        return !groupChat.getReceivers().contains(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
