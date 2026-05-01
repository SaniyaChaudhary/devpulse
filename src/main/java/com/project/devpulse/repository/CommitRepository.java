//package com.project.devpulse.repository;
//
//import com.project.devpulse.model.Commit;
//import com.project.devpulse.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//public interface CommitRepository extends JpaRepository<Commit, Long> {
//
//    // Basic fetch
//    List<Commit> findByUser(User user);
//
//    // Filter by date range
//    List<Commit> findByUserAndCommittedAtBetween(
//            User user,
//            LocalDateTime start,
//            LocalDateTime end
//    );
//
//    // Find by SHA
//    Optional<Commit> findByCommitSha(String commitSha);
//
//    // Exists check (useful but avoid in loops)
//    boolean existsByCommitSha(String commitSha);
//
//    // PERFORMANCE FIX (IMPORTANT)
//    @Query("SELECT c.commitSha FROM Commit c WHERE c.user = :user")
//    Set<String> findAllCommitShasByUser(User user);
//
//    // Count commits (useful for dashboard/stats)
//    long countByUser(User user);
//
//    // Recent commits
//    List<Commit> findTop10ByUserOrderByCommittedAtDesc(User user);
//}