package com.feelog.Entity;

import com.feelog.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
//@Table(name = "member_aaaa")
public class Member {
    @Id     //  primary key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(nullable = false , unique = true) //not null, unique
    private String email; // 로그인 아이디

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name; // 회원 이름

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp  // 최초 데이터 저장 시간
    private LocalDateTime createdAt;

    @UpdateTimestamp // 데이터 수정 시간
    private LocalDateTime updatedAt;


}
