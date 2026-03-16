package com.feelog.service;

import com.feelog.Entity.Diary;
import com.feelog.Entity.DiaryLike;
import com.feelog.Entity.Member;
import com.feelog.repository.DiaryLikeRepository;
import com.feelog.repository.DiaryRepository;
import com.feelog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryLikeService {

    private final DiaryLikeRepository diaryLikeRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    public boolean toggleEmpathy(Long diaryId, String email) {
        // 현재 로그인 한 회원 과  공감 일기 조회 하기
        Member member = memberRepository.findByEmail(email);
        Diary diary = diaryRepository.findById(diaryId).get();

        // 현재 회원이  공감을 추가 하는것인지 회수하는것인지 조회
        DiaryLike diaryLike = diaryLikeRepository
                .findByDiaryIdAndMemberId(diaryId,member.getId());
        System.out.println( diaryLike );
        if(diaryLike == null){ // null이면 테이블에 없으니까 공감 추가
            DiaryLike like = new DiaryLike();
            like.setDiary(diary);
            like.setMember(member);
            diaryLikeRepository.save(like); // 테이블 저장
            return true;
        }else{ //공감 삭제
            diaryLikeRepository.delete(diaryLike);
            return false;
        }
    }

    // 현재 일기의  공감수 몇개?
    public long getEmpathyCount(Long diaryId) {
        return diaryLikeRepository.countByDiaryId(diaryId);
    }
}
