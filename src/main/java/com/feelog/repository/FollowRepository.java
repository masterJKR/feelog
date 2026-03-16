package com.feelog.repository;

import com.feelog.Entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    Follow findByFollowerIdAndFollowingId(Long id, Long wingId);
}
