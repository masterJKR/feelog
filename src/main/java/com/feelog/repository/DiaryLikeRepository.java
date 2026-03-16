package com.feelog.repository;

import com.feelog.Entity.DiaryLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryLikeRepository extends JpaRepository<DiaryLike,Long> {
    DiaryLike findByDiaryIdAndMemberId(Long diaryId, Long id);

    long countByDiaryId(Long diaryId);
}
