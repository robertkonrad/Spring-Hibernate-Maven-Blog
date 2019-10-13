package com.robertkonrad.blog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post", schema = "blog")
public class Post {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "last_modificated")
	private Date lastModificated;
	
	public Post() {
		
	}

	public Post(String title, String description, String author, Date createdDate, Date lastModificated) {
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
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
