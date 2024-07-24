package com.kitcat.likelion.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

//    private String potoName;

    private int commentCount;

    private int like_count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = {PERSIST,REMOVE}, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {PERSIST,REMOVE}, orphanRemoval = true)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {PERSIST,REMOVE}, orphanRemoval = true)
    private List<PostScrap> scraps = new ArrayList<>();

    @Builder
    public Post(String title, String content, int commentCount, int like_count, User user) {
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.like_count = like_count;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
        if(!user.getPosts().contains(this)) {
            user.addPost(this);
        }
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void increaseCommentCount(int increment) {
        commentCount = increment;
    }

}
