package com.feelog.repository;

import com.feelog.Entity.Diary;
import com.feelog.constant.Emotion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByOrderByIdDesc(Pageable pageable);

    List<Diary> findByEmotionOrderByIdDesc(Emotion emotion, Pageable pageable);

    @Query("""
        select d from Diary d
        where ( :emotion is null or d.emotion = :emotion)
            and (
                :keyword is null or :keyword = '' 
                or d.title like %:keyword%
                or d.content like %:keyword%
                or d.noteToMyself like %:keyword%    
            )
        order by d.id desc
    """)
    List<Diary> searchDiary(String keyword, Emotion emotion, Pageable pageable);
}
