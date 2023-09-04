package com.example.quizapp.controller;

import com.example.quizapp.dao.request.PostRequest;
import com.example.quizapp.dto.FeedbackDTO;
import com.example.quizapp.dto.PostDTO;
import com.example.quizapp.mapper.PostMapper;
import com.example.quizapp.model.Post;
import com.example.quizapp.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource/post")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Post", description = "The Post API. Contains all the operations that can be performed on a post.")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Show all posts")
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Add post")
    @PostMapping
    public ResponseEntity<PostDTO> addPost(@RequestBody PostRequest post){
        return ResponseEntity.ok(postMapper.toDto(postService.addPost(post)));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Delete post with ID")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        return ResponseEntity.ok(postService.deletePost(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Update post with ID")
    @PutMapping("{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostDTO post){
        return ResponseEntity.ok(postService.updatePost(id, post));
    }
}
