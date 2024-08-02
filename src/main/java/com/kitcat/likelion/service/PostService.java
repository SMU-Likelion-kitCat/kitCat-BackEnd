package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kitcat.likelion.domain.*;
import com.kitcat.likelion.repository.*;
import com.kitcat.likelion.requestDTO.PostCommentRequestDTO;
import com.kitcat.likelion.requestDTO.PostCreateRequestDTO;
import com.kitcat.likelion.responseDTO.PostCommentResponseDTO;
import com.kitcat.likelion.responseDTO.PostDetailDTO;
import com.kitcat.likelion.responseDTO.PostListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;
    private final PhotoRepository photoRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String>  uploadToS32(List<MultipartFile> files, String nickname) {
        List<String> fileNames = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // 최적의 스레드 풀 크기

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String current_date = now.format(dateTimeFormatter);

        try {
            AtomicInteger count = new AtomicInteger(1); // 고유한 파일 번호를 생성하기 위한 AtomicInteger

            CompletableFuture<?>[] futures = files.stream()
                    .map(file -> CompletableFuture.runAsync(() -> {
                        try {
                            String fileName = nickname + current_date + count.getAndIncrement();

                            ObjectMetadata metadata = new ObjectMetadata();

                            metadata.setContentLength(file.getSize());
                            metadata.setContentType(file.getContentType());

                            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
                            System.out.println("사진 URL" + amazonS3.getUrl(bucket, fileName));
                            fileNames.add(fileName);
                        } catch (IOException e) {
                            // 에러 처리
                        }
                    }, executor))
                    .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).join(); // 모든 작업이 완료될 때까지 대기
        } finally {
            executor.shutdown(); // ExecutorService 종료
        }

        return fileNames;
    }

    @Transactional
    public void createPost(Long userId, PostCreateRequestDTO requestDTO, List<MultipartFile> files){
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

        if(files != null) {
            List<String> fileNames = uploadToS32(files, user.getNickname());
            post.setPhotoName(fileNames.get(0));

            for(String fileName : fileNames){
                Photo photo = Photo.createPhoto(fileName, post);
                photoRepository.save(photo);
            }
        }

    }

    @Transactional
    public String createComment(Long userId, PostCommentRequestDTO requestDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));

        Post post = postRepository.findById(requestDTO.getPostId())
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + requestDTO.getPostId()));

        Comment comment = new Comment(requestDTO.getContent(), false);
        comment.setUser(user);
        comment.setPost(post);

        if (requestDTO.getParentId() != null){
            if(commentRepository.findById(requestDTO.getParentId()).isPresent()){
                Comment parentComment = commentRepository.findById(requestDTO.getParentId())
                        .orElseThrow(() -> new NotFoundException("Could not find parent comment with id: " + requestDTO.getParentId()));
                comment.setParent(parentComment);
            }else{
                return "not found parentComment";
            }
        }

        commentRepository.save(comment);

        post.increaseCommentCount();
        postRepository.save(post);
        return "good";
    }
    @Transactional
    public PostDetailDTO findDetailPost(Long userId, Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not find post with id: " + postId));

        List<PostCommentResponseDTO> comments = commentRepository.findByPostId(postId, userId);
        List<String> photoNames = photoRepository.findPhotoNameByPostId(postId);

        Optional<Heart> heart = heartRepository.findByUserIdAndPostId(userId, postId);

        return PostDetailDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .comments(comments)
                .likeCount(post.getLike_count())
                .commentCount(post.getCommentCount())
                .createTime(post.getCreateTime())
                .writer(post.getUser().getNickname())
                .heartState(heart.isPresent())
                .photoName(photoNames)
                .build();

    }

    @Transactional
    public String insertHeart(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found post with id: " + postId));

        if(heartRepository.findByUserAndPost(user, post).isPresent()){
            return "fail to add heart to post";
        }

        Heart heart = new Heart();
        heart.setPost(post);
        heart.setUser(user);

        heartRepository.save(heart);
        postRepository.increaseHeart(postId);
        return "AddHeart success";
    }

    @Transactional
    public String deleteHeart(Long userId, Long postId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));

            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new NotFoundException("Could not found post with id: " + postId));

            Heart heart = heartRepository.findByUserAndPost(user, post)
                    .orElseThrow(() -> new NotFoundException("Could not find heart with id: " + postId));

            user.getHearts().remove(heart);
            postRepository.decreaseHeart(postId);
            heartRepository.delete(heart);

            return "DeleteHeart success";

        } catch (NotFoundException e) {
            return "fail to delete heart to post";
        }
    }


    @Transactional
    public List<PostListDTO> postList(){
        List<PostListDTO> posts = postRepository.findAllPostListDTO();
        return posts;
    }

//    public String checkHeart(Long userId, Long postId){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("Could not found user with id: " + userId));
//
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new NotFoundException("Could not found post with id: " + postId));
//
//        if(heartRepository.findByUserAndPost(user, post).isPresent()){
//            return "fail";
//        }
//        return "Success";
//    }

}
