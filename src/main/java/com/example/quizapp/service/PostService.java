package com.example.quizapp.service;

import com.example.quizapp.dao.request.PostRequest;
import com.example.quizapp.dto.PostDTO;
import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.model.Post;
import com.example.quizapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post addPost(PostRequest post) {
        Post newPost = new Post();
        newPost.setId(post.getId());
        newPost.setTitle(post.getTitle());
        newPost.setTags(post.getTags());
        newPost.setText(post.getText());
        newPost.setCreatedAt(LocalDateTime.now());
        postRepository.save(newPost);
        return newPost;
    }

    public String deletePost(Long id) {
        postRepository.deleteById(id);
        return "Post with id was deleted.";
    }

    public Post updatePost(Long id, PostDTO post) {
        updatePost(id, post);
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_POST));
    }
}
