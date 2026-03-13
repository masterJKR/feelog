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

    // 일기 삭제  요청
    @PostMapping("/delete/{id}")
    public String deleteDiary(@PathVariable Long id,
                              Principal principal){

        diaryService.deleteDiary( id );
        // 일기작성자와 로그인사용자가 같은가  확인하는거 필요하다.

        return "redirect:/diary";  // 일기 삭제 하고 목록으로 보내기
    }



    // 일기 수정 요청
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id,
                       Principal principal) {

        DiaryDto diaryDto = diaryService.getMyDiary( id , principal.getName() );

        if ( diaryDto == null ) { // 로그인한 사람과 작성자가 다른경우 - 잘못된 접근
            return """
                    <script>
                        alert('잘못된 접근입니다. 작성자정보와 일치하지않아');
                        location.href='/diary';
                    </script>  
                    """;
        }

        model.addAttribute("emotionList", Emotion.values());
        model.addAttribute("moodWeatherList", MoodWeather.values());

        model.addAttribute("diaryDto", diaryDto);

        return "diary/form";  // 새일기 작성 페이지는  수정페이지로 할수 있다.
                              // 같은 클래스 객체를 다루기 때문에
    }


    //  일기 상세 페이지 요청
    @GetMapping("/detail/{id}")
    public String diaryDetail(@PathVariable Long id,
                              Model model, Principal principal) {
        DiaryDetailDto diaryDetailDto = diaryService.getDiary( id ,principal.getName() );

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