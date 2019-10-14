package com.robertkonrad.blog.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "post", schema = "blog")
public class Post {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "title")
	private String title;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "description")
	private String description;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "author")
	private User author;
	
	@NotNull
	@Column(name = "created_date")
	private Date createdDate;
	
	@NotNull
	@Column(name = "last_modificated")
	private Date lastModificated;
	
	public Post() {
		
	}

	public Post(String title, String description, User author, Date createdDate, Date lastModificated) {
		super();
		this.title = title;
		this.description = description;
		this.author = author;
		this.createdDate = createdDate;
		this.lastModificated = lastModificated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", description=" + description + ", author=" + author
				+ ", createdDate=" + createdDate + ", lastModificated=" + lastModificated + "]";
	}
	
	
}
