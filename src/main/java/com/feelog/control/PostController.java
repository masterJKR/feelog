package com.feelog.control;

import com.feelog.Dto.DiaryDetailDto;
import com.feelog.Dto.DiaryDto;
import com.feelog.Dto.DiaryListDto;
import com.feelog.constant.Emotion;
import com.feelog.constant.MoodWeather;
import com.feelog.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/diary")
@RequiredArgsConstructor
public class PostController {

    private final DiaryService diaryService;

    //  일기 상세 페이지 요청
    @GetMapping("/detail/{id}")
    public String diaryDetail(@PathVariable Long id,
                              Model model, Principal principal) {
        DiaryDetailDto diaryDetailDto = diaryService.getDiary( id );

        model.addAttribute("diary", diaryDetailDto);
        return "diary/detail";
    }


    // 일기 목록 페이지 요청- 감정태그, 검색 가능
    @GetMapping
    public String diaryList(Model model, Principal principal,
                            @RequestParam(value="page", defaultValue = "1") int page ,
                            @RequestParam(value="keyword", required = false) String keyword,
                            @RequestParam(value="emotion", required = false) Emotion emotion) {

        Pageable pageable = PageRequest.of( page-1 , 9);

        Page<DiaryListDto> diaryListDtos = diaryService.getDiaryList( keyword, emotion, pageable );

        model.addAttribute("keyword", keyword);
        model.addAttribute("emotion", emotion);
        model.addAttribute("diaryList", diaryListDtos);
        return "diary/list";
    }


    @GetMapping("/new")  //   주소 -> /diary/new
    public String formPage(Model model){
        model.addAttribute("diaryDto", new DiaryDto());

        model.addAttribute("emotionList", Emotion.values());
        model.addAttribute("moodWeatherList", MoodWeather.values());
        return "diary/form";
    }

    @PostMapping("/new")
    public String saveDiary(@Valid DiaryDto diaryDto,
                            BindingResult bindingResult,
                            Principal principal){
        if(bindingResult.hasErrors()){
            return "diary/form";
        }
        // 유효값 체크 문제 없으면  저장하기
        diaryService.saveDiary(diaryDto , principal.getName()  );

        return "redirect:/"; // 저장후 첫페이지 이동
    }
}

// 감정일기 작성, 목록, 상세, 수정, 삭제
//  /diary/**
// 댓글 , 공감 , 팔로우