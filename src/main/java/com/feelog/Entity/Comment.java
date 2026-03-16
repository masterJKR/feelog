package com.feelog.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id", nullable = false)
    private Diary diary; // 어떤 일기의 댓글인가

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name ="member_id", nullable = false)
    private Member member; // 누가 댓글작성

    @CreationTimestamp
    private LocalDateTime createdAt;
}
