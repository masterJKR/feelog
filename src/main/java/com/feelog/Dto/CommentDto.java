package com.feelog.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String content;
    private String writerName;  // 댓글 작성자
    private LocalDateTime createdAt;
    private boolean myComment; // 내일기에 내가 댓글작성
    private Long writerId;  // 댓글 작성자  PK

    private boolean reportable;  // 신고 가능여부
}
