package io.diaz.pinoystack.repo;

import io.diaz.pinoystack.models.Post;
import io.diaz.pinoystack.models.Subforum;
import io.diaz.pinoystack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findAllBySubforum(Subforum subforum);

    List<Post> findByUser(User user);
}
