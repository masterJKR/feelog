package com.feelog.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="follower_id", nullable = false)
    private Member follower;  // 팔로우 하는 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="following_id",nullable = false)
    private Member following; // 팔로우 당하는 회원

    @CreationTimestamp
    private LocalDateTime createdAt;
}
