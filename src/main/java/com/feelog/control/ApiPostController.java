package com.feelog.control;

import com.feelog.Entity.Follow;
import com.feelog.service.DiaryLikeService;
import com.feelog.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiPostController {

    private final DiaryLikeService diaryLikeService;
    private final FollowService followService;

    // 팔로우 신청, 취소
    @PostMapping("/follow")
    @ResponseBody
    public Map<String, Object> follow(
            @RequestBody Map<String, Long> body,
                                      Principal principal) {
        Long wingId= body.get("id");
        String email = principal.getName();
        boolean isFollow = followService.followSave(wingId, email);
        Map<String, Object> map = new HashMap<>();
        map.put("isFollow", isFollow);
        return map;
    }

    // 공감 하기, 취소
    @PostMapping("/empathy")
    @ResponseBody
    public Map<String, Object> empathy(
            @RequestBody Map<String, Long> data,
            Principal principal){
        Long diaryId = data.get("id"); // 자바스크립트객체의 key입력해야 value나옴
        // 로그인한 회원 가져오기
        String email = principal.getName();
        //  일기id 와  로그인 계정명을 서비스에 보내 공감 증감 시키기
        boolean isLike = diaryLikeService.toggleEmpathy(diaryId, email);
        long count = diaryLikeService.getEmpathyCount(diaryId);

        Map<String, Object> result = new HashMap<>();
        result.put("isLike", isLike);
        result.put("count", count);

        return result;
    }
}
