package com.robertkonrad.blog.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post", schema = "blog")
public class Post {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@NotEmpty(message = "Title cannot be empty.")
	@Size(max = 100)
	@Column(name = "title")
	private String title;
	
	@NotNull
	@NotEmpty(message = "Description cannot be empty.")
	@Size(max = 8000)
	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "author")
	private User author;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "last_modificated")
	private Date lastModificated;

	@Column(name = "image")
	private String image;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	public Post() {
		
	}

	public Post(int id, @NotNull @Size(max = 100) String title, @NotNull @Size(max = 255) String description,
			@NotNull User author, @NotNull Date createdDate, @NotNull Date lastModificated, String image) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.author = author;
		this.createdDate = createdDate;
		this.lastModificated = lastModificated;
		this.image = image;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", description=" + description + ", author=" + author
				+ ", createdDate=" + createdDate + ", lastModificated=" + lastModificated + ", image="
				+ image + "]";
	}

	
	
	
}
