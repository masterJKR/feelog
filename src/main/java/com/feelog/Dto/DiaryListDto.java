package com.feelog.Dto;

import com.feelog.constant.Emotion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiaryListDto { private Long id;
    private LocalDate diaryDate;
    private Emotion emotion;
    private String title;
    private String content;
    private Integer emotionScore;
    private boolean publicVisible;

}
