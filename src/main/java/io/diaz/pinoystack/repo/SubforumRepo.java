package io.diaz.pinoystack.repo;

import io.diaz.pinoystack.models.Subforum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SubforumRepo extends JpaRepository<Subforum, Long> {

    Optional<Subforum> findByName (String subforumName);
}
