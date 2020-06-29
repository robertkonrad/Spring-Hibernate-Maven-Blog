package com.robertkonrad.blog.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "tag", schema = "blog")
public class Tag {

    @NotNull
    @NotEmpty
    @Id
    @Size(max = 20)
    @Column(name = "tag", length = 20, unique = true)
    private String tag;

    @OneToMany(mappedBy = "post")
    private Set<PostTag> post;

    public Tag() {

    }

    public Tag(@NotNull @NotEmpty @Size(max = 20) String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag='" + tag + '\'' +
                '}';
    }
}
