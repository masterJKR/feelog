package com.feelog.control;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/diary")
@RequiredArgsConstructor
public class PostController {

    private final DiaryService diaryService;

    @GetMapping
    public String diaryList(Model model, Principal principal,
                            @RequestParam("page") Optional<Integer> page) {

        Pageable pageable = PageRequest.of( page.isPresent() ? page.get() : 0, 9);

        Page<DiaryListDto> diaryListDtos = diaryService.getDiaryList( pageable );

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