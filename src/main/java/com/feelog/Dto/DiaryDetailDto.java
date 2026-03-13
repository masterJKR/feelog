package com.feelog.Dto;

import com.feelog.constant.Emotion;
import com.feelog.constant.MoodWeather;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DiaryDetailDto {
    private Long id;// 일기 PK
    private String title;  // 제목
    private String content;  // 내용
    private String noteToMySelf;  // 오늘의 한줄

    private Emotion emotion; // 감정
    private LocalDate diaryDate;  // 일기 날짜
    private MoodWeather moodWeather; // 마음의 날씨
    private Integer emotionScore; // 마음 온도
    private boolean publicVisible; // 공개여부
    private boolean allowComment; //댓글 가능 여부
    private LocalDateTime createdAt; // 작성일
    private LocalDateTime updatedAt; // 수정일

    private String writerName; // 일기 작성자 이름
    private String writerEmail;  // 일기 작성자 이메일
    private String emotionLabel;
    private String emotionEmoji;

    // 공감 정보
    private int empathyCount; // 공감수
    private boolean empathizedByMe; // 로그인 사용자가 공감했는가?

    private List<CommentDto> commentDtoList;

    private boolean  me;  // 일기 상세 보는 사람이 작성자인가?
    private Long writerId; // 작성자 PK

}
