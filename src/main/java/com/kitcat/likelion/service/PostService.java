package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kitcat.likelion.domain.*;
import com.kitcat.likelion.repository.*;
import com.kitcat.likelion.requestDTO.PostCommentRequestDTO;
import com.kitcat.likelion.requestDTO.PostCreateRequestDTO;
import com.kitcat.likelion.responseDTO.PostCommentResponesDTO;
import com.kitcat.likelion.responseDTO.PostDetailDTO;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;
    private final PostScrapRepository postScrapRepository;

    @Transactional
    public void createPost(Long userId, PostCreateRequestDTO requestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));

        Post post = Post.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .commentCount(0)
                .like_count(0)
                .build();

        post.setUser(user);

        postRepository.save(post);
    }

    @Transactional
    public void createComment(Long userId, PostCommentRequestDTO requestDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));

        Post post = postRepository.findById(requestDTO.getPostId())
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + requestDTO.getPostId()));

        Comment comment = new Comment(requestDTO.getContent(), false);
        comment.setUser(user);
        comment.setPost(post);

        if (requestDTO.getParentId() != null){
            Comment parentComment = commentRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new NotFoundException("Could not find parent comment with id: " + requestDTO.getParentId()));
            comment.setParent(parentComment);
        }

        commentRepository.save(comment);

        if (requestDTO.getParentId() != null){
            post.increaseCommentCount(post.getCommentCount()+1);
        }
    }
    @Transactional
    public PostDetailDTO findDetailPost(Long userId, Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not find post with id: " + postId));

        List<PostCommentResponesDTO> comments = commentRepository.findByPostId(postId);

        Optional<Heart> heart = heartRepository.findByUserIdAndPostId(userId, postId);
        Optional<PostScrap> postScrap = postScrapRepository.findByUserIdAndPostId(userId, postId);

        return PostDetailDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .comments(comments)
                .likeCount(post.getLike_count())
                .commentCount(post.getCommentCount())
                .createTime(post.getCreateTime())
                .writer(post.getUser().getNickname())
                .scrapState(postScrap.isPresent())
                .heartState(heart.isPresent())
                .build();

    }

    @Transactional
    public void insertHeart(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found post with id: " + postId));

        if(heartRepository.findByUserAndPost(user, post).isPresent()){
            throw new RuntimeException("failed to add heart to post");
        }

        Heart heart = new Heart();
        heart.setPost(post);
        heart.setUser(user);

        heartRepository.save(heart);
        postRepository.increaseHeart(postId);
    }

    @Transactional
    public void deleteHeart(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found post with id: " + postId));

        Heart heart = heartRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new NotFoundException("Could not find heart with id: " + postId));

        user.getHearts().remove(heart);
        postRepository.decreaseHeart(postId);
        heartRepository.delete(heart);

    }

    @Transactional
    public void insertScrap(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found post with id: " + postId));

        if(postScrapRepository.findByUserAndPost(user, post).isPresent()){
            throw new RuntimeException("failed to add scrap to post");
        }

        PostScrap postScrap = new PostScrap();
        postScrap.setPost(post);
        postScrap.setUser(user);
        postScrapRepository.save(postScrap);

    }

    @Transactional
    public void deleteScrap(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found post with id: " + postId));

        PostScrap postScrap = postScrapRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new NotFoundException("Could not find post scrap with id: " + postId));

        user.getScraps().remove(postScrap);
        postScrapRepository.delete(postScrap);

    }


}
