package com.robertkonrad.blog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comment", schema = "blog")
public class Comment {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@NotEmpty(message = "Description cannot be empty.")
	@Size(max = 8000, min = 1)
	@Column(name = "description")
	private String description;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "last_modificated")
	private Date lastModificated;

	@ManyToOne
	@JoinColumn(name = "author")
	private User author;

	@ManyToOne
	@JoinColumn(name = "post")
	private Post post;
	
	public Comment() {
		
	}

	public Comment(int id, @NotNull @Size(max = 8000) String description, @NotNull Date createdDate,
			@NotNull Date lastModificated, @NotNull User author, @NotNull Post post) {
		super();
		this.id = id;
		this.description = description;
		this.createdDate = createdDate;
		this.lastModificated = lastModificated;
		this.author = author;
		this.post = post;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModificated() {
		return lastModificated;
	}

	public void setLastModificated(Date lastModificated) {
		this.lastModificated = lastModificated;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", description=" + description + ", createdDate=" + createdDate
				+ ", lastModificated=" + lastModificated + ", author=" + author + ", post=" + post + "]";
	}
	
	
}
