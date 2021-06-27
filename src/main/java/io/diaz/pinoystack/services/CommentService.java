package io.diaz.pinoystack.services;

import io.diaz.pinoystack.dto.CommentsDto;
import io.diaz.pinoystack.exceptions.PostNotFoundException;
import io.diaz.pinoystack.mapper.CommentMapper;
import io.diaz.pinoystack.models.Comment;
import io.diaz.pinoystack.models.NotificationEmail;
import io.diaz.pinoystack.models.Post;
import io.diaz.pinoystack.models.User;
import io.diaz.pinoystack.repo.CommentRepo;
import io.diaz.pinoystack.repo.PostRepo;
import io.diaz.pinoystack.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL = "";

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepo commentRepo;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepo.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepo.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername()
                + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post.",
                user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepo.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepo.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
