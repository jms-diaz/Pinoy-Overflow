package io.diaz.pinoystack.repo;

import io.diaz.pinoystack.models.Post;
import io.diaz.pinoystack.models.User;
import io.diaz.pinoystack.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);}
