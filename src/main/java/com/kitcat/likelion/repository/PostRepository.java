package com.kitcat.likelion.repository;


import com.kitcat.likelion.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {


}
