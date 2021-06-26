package io.diaz.pinoystack.controllers;


import io.diaz.pinoystack.dto.PostRequest;
import io.diaz.pinoystack.dto.PostResponse;
import io.diaz.pinoystack.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/by-subforum/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubforum(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostsBySubforum(id));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }
}
