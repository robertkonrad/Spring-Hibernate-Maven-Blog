package com.robertkonrad.blog.entity;

import com.robertkonrad.blog.validation.UserMatchesPassword;
import com.robertkonrad.blog.validation.UserPassword;
import com.robertkonrad.blog.validation.UserUsername;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users", schema = "blog")
@UserMatchesPassword(groups = {Group1.class, Group2.class})
public class User {

    @UserUsername(groups = {Group1.class})
    @Id
    @NotNull(groups = {Group1.class})
    @Size(max = 20, message = "Username length must be between 0 and 20 chars.", groups = {Group1.class})
    @NotEmpty(message = "Username cannot be empty.", groups = {Group1.class})
    @Column(name = "username", length = 20)
    private String username;

    @UserPassword(groups = {Group1.class, Group2.class})
    @Size(max = 68, groups = {Group1.class, Group2.class})
    @NotNull(groups = {Group1.class, Group2.class})
    @Column(name = "password", length = 68)
    private String password;

    @Transient
    private String matchingPassword;

    @NotNull
    @Column(name = "enabled", columnDefinition = "integer default 1")
    private int enabled = 1;

    @OneToOne(mappedBy = "username", cascade = CascadeType.ALL)
    private Role role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public User() {

    }

    public User(String username, String password, int enabled) {
        super();
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", enabled=" + enabled + "]";
    }


}
