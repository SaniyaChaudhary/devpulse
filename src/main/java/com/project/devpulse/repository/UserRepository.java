//package com.project.devpulse.repository;
//
//import com.project.devpulse.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByGithubId(Long githubId);
//    Optional<User> findByGithubUsername(String username);
//}