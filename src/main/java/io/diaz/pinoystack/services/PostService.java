package io.diaz.pinoystack.services;

import io.diaz.pinoystack.dto.PostRequest;
import io.diaz.pinoystack.dto.PostResponse;
import io.diaz.pinoystack.exceptions.PinoyStackException;
import io.diaz.pinoystack.exceptions.PostNotFoundException;
import io.diaz.pinoystack.exceptions.SubforumNotFoundException;
import io.diaz.pinoystack.mapper.PostMapper;
import io.diaz.pinoystack.models.Post;
import io.diaz.pinoystack.models.Subforum;
import io.diaz.pinoystack.models.User;
import io.diaz.pinoystack.repo.PostRepo;
import io.diaz.pinoystack.repo.SubforumRepo;
import io.diaz.pinoystack.repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepo postRepo;
    private final SubforumRepo subforumRepo;
    private final UserRepo userRepo;
    private final AuthService authService;
    private final PostMapper postMapper;

    public Post save(PostRequest postRequest) {
        Subforum subforum = subforumRepo.findByName(postRequest.getSubforumName())
                .orElseThrow(() -> new PinoyStackException(postRequest.getSubforumName()));
        User currentUser = authService.getCurrentUser();
        return postRepo.save(postMapper.map(postRequest, subforum, currentUser));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepo.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubforum(Long subforumId) {
        Subforum subforum = subforumRepo.findById(subforumId)
                .orElseThrow(() -> new SubforumNotFoundException(subforumId.toString()));
        List<Post> posts = postRepo.findAllBySubforum(subforum);
        return posts.stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepo.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
