package com.gr.learningpath.repositories;

import com.gr.learningpath.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFromUserId(Long userId);

    @Query("SELECT f FROM Follow f WHERE f.fromUser.id = ?1 AND f.toUser = ?2")
    Optional<Follow> findByFromUserAndToUser(Long fromUser, Long toUser);
}
