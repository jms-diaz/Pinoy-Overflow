package io.diaz.pinoystack.services;

import io.diaz.pinoystack.dto.VoteDto;
import io.diaz.pinoystack.exceptions.PinoyStackException;
import io.diaz.pinoystack.exceptions.PostNotFoundException;
import io.diaz.pinoystack.models.Post;
import io.diaz.pinoystack.models.Vote;
import io.diaz.pinoystack.repo.PostRepo;
import io.diaz.pinoystack.repo.VoteRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.diaz.pinoystack.models.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepo voteRepo;
    private final PostRepo postRepo;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post  = postRepo.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with id - "
                        + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepo.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new PinoyStackException("You've already " +
                    voteDto.getVoteType().toString().toLowerCase() + "d this post.");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepo.save(mapToVote(voteDto, post));
        postRepo.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
