package com.robertkonrad.blog.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", schema = "blog")
public class User {

	@Id
	@Column(name = "username")
	private String username;
	
	@Size(max = 68)
	@NotNull
	@Column(name = "password")
	private String password;
	
	@NotNull
	@Column(name = "enabled", columnDefinition = "integer default 1")
	private int enabled = 1; 
	
	@OneToOne(mappedBy = "username", cascade = CascadeType.ALL)
	private Role role;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Post> posts;
	
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

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", enabled=" + enabled + "]";
	}
	
	
}
