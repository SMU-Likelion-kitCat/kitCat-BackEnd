package com.kitcat.likelion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "photo_id")
    private Long id;

    private String photoName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void setPost(Post post) {
        this.post = post;

        if(!post.getPhotos().contains(this)) {
            post.addPhoto(this);
        }
    }

    public static Photo createPhoto(String fileName, Post post) {
        Photo photo = new Photo(fileName);
        photo.setPost(post);
        return photo;
    }

    public Photo(String photoName) {
        this.photoName = photoName;
    }


}
