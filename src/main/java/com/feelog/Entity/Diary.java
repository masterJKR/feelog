package com.feelog.Entity;

import com.feelog.constant.Emotion;
import com.feelog.constant.MoodWeather;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id" )
    private Member member;

    // 일기 날짜
    @Column(nullable = false)
    private LocalDate diaryDate;

    // 감정
    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    // 한줄 제목
    @Column(nullable = false, length = 100)
    private String title;

    // 기분 날씨
    @Enumerated(EnumType.STRING)
    private MoodWeather moodWeather;

    // 마음 점수
    @Column(nullable = false)
    private Integer emotionScore;

    // 일기 내용 (summernote html)
    @Column(columnDefinition = "TEXT")
    private String content;

    // 공개 여부
    private Boolean publicVisible = false;

    // 댓글 허용 여부
    private Boolean allowComment = false;

    // 나에게 한마디
    @Column(length = 200)
    private String noteToMyself;

    // 생성일
    @CreatedDate
    private LocalDateTime createdAt;

    // 수정일
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
