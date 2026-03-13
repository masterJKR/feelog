package com.feelog.Dto;

import com.feelog.constant.Emotion;
import com.feelog.constant.MoodWeather;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class DiaryDto {
    private Long id;

    @NotNull(message = "작성일을 선택해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate diaryDate;

    @NotNull(message = "오늘의 감정을 선택해주세요.")
    private Emotion emotion;

    @NotBlank(message = "오늘의 한 줄을 입력해주세요.")
    @Size(max = 100, message = "한 줄 제목은 100자 이하로 입력해주세요.")
    private String title;

    @NotNull(message = "오늘의 기분 날씨를 선택해주세요.")
    private MoodWeather moodWeather;

    @NotNull(message = "마음 온도를 입력해주세요.")
    @Min(value = 1, message = "마음 온도는 1 이상이어야 합니다.")
    @Max(value = 100, message = "마음 온도는 100 이하이어야 합니다.")
    private Integer emotionScore;

    @NotBlank(message = "오늘의 기록을 작성해주세요.")
    @Size(min = 10, message = "내용은 최소 10자 이상 작성해주세요.")
    private String content;

    private Boolean publicVisible = false;

    private Boolean allowComment = false;

    @Size(max = 200, message = "나에게 한마디는 200자 이하로 작성해주세요.")
    private String noteToMyself;
}
