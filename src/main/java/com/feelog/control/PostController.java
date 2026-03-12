package com.feelog.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/diary")
public class PostController {

    @GetMapping("/new")  //   주소 -> /diary/new
    public String formPage(){

        return "diary/form";
    }
}

// 감정일기 작성, 목록, 상세, 수정, 삭제
//  /diary/**
// 댓글 , 공감 , 팔로우