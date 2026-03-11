package com.feelog.control;

import com.feelog.Dto.SignupDto;
import com.feelog.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //로그인 페이지
    @GetMapping("/signIn")
    public String signIn(){
        return "auth/signIn";
    }


    // 회원가입 입력데이터 처리
    @PostMapping("/signUp")
    public String signUp(@Valid SignupDto signupDto,
                         BindingResult bindingResult,
                         Model model) {
        if(bindingResult.hasErrors()) { // 유효값체크 문제있으면
            return "auth/signUp";
        }
        // 유효한 데이터라면  테이블에 저장 하기
        try {
            memberService.saveData(signupDto, passwordEncoder);
        }catch(Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/signUp";
        }
        return "redirect:/signIn";
    }

    // 회원가입 페이지 제공
    @GetMapping("/signUp")
    public String signUp(Model model){
        model.addAttribute("signupDto", new SignupDto());
        return "auth/signUp";
    }
}
