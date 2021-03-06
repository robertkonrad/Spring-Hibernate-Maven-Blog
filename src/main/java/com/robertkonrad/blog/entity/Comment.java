package com.robertkonrad.blog.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "comment", schema = "blog")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotEmpty(message = "Description cannot be empty.")
    @Size(max = 2000, min = 1, message = "Max length is 2000 chars.")
    @Column(name = "description", length = 2000)
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

    public Comment(@NotNull @Size(max = 8000) String description) {
        this.description = description;
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
