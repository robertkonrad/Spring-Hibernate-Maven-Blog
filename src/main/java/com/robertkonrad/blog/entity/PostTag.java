package com.robertkonrad.blog.entity;

import javax.persistence.*;

@Entity
@Table(name = "post_tag", schema = "blog")
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag")
    private Tag tag;

}
