package com.feelog.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
    private Long diaryId;
    private String content;
}
