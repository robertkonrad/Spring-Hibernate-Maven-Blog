package com.robertkonrad.blog.entity;

import com.robertkonrad.blog.validation.UserMatchesPassword;
import com.robertkonrad.blog.validation.UserPassword;
import com.robertkonrad.blog.validation.UserUsername;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", schema = "blog")
@UserMatchesPassword
public class User {

	@UserUsername
	@Id
	@NotNull(message = "Username cannot be empty.")
	@NotEmpty(message = "Username cannot be empty.")
	@Column(name = "username")
	private String username;

	@UserPassword
	@Size(max = 68)
	@NotNull
	@Column(name = "password")
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
