package io.diaz.pinoystack.repo;

import io.diaz.pinoystack.models.Comment;
import io.diaz.pinoystack.models.Post;
import io.diaz.pinoystack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
