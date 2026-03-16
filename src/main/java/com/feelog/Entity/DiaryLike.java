package com.feelog.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name ="diary_like",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"diary_id","member_id"}))
public class DiaryLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id", nullable = false)
    private Diary diary;  // 어떤 일기에 공감인가?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member; // 누가 공감 했나?

    @CreationTimestamp
    private LocalDateTime createdAt;
}
