package com.project.devpulse.repository;

import com.project.devpulse.model.Commit;
import com.project.devpulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommitRepository extends JpaRepository<Commit, Long> {
    List<Commit> findByUser(User user);

    List<Commit> findByUserAndCommittedAtBetween(
            User user,
            LocalDateTime start,
            LocalDateTime end
    );

    Optional<Commit> findByCommitSha(String commitSha);

    boolean existsByCommitSha(String commitSha);

}
