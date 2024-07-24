package com.kitcat.likelion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ColumnDefault("FALSE")
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    public Comment(String content, boolean isDeleted) {
        this.content = content;
        this.isDeleted = isDeleted;
    }

    public void setUser(User user) {
        this.user = user;
        if(!user.getComments().contains(this)) {
            user.getComments().add(this);
        }
    }

    public void setPost(Post post) {
        this.post = post;
        if(!post.getComments().contains(this)) {
            post.getComments().add(this);
        }
    }

    public void setParent(Comment comment) {
        this.parent = comment;

        if(!comment.children.contains(this)) {
            comment.children.add(this);
        }
    }

}
