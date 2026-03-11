package com.feelog.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MemberProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // member 테이블과 관계는  1:1 이다
    // mappedBy 는  관계 맺을 테이블 누가 주인인지
    // cascade = CascadeType.ALL 은 회원 삭제시 프로필도 같이 처리
    // orphanRemoval = true  은  연결 끊긴 프로필 제거
    //@OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id" , nullable = false,unique = true)
    private Member member;


    private String bio; // 자기소개
    private String statusMessage; // 상태 메시지
    private String profileImageUrl; // 프로필 이미지

    @Column(nullable = false)
    private boolean profileEnabled; // 프로필 공개 여부
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
