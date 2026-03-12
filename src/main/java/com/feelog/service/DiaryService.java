package com.feelog.service;

import com.feelog.Dto.DiaryDto;
import com.feelog.Entity.Diary;
import com.feelog.Entity.Member;
import com.feelog.repository.DiaryRepository;
import com.feelog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public void saveDiary(DiaryDto diaryDto , String email){
        Diary diary = modelMapper.map(diaryDto, Diary.class);
        // 누가 작성했는가  현재 로그인한 계정의  객체를 테이블로 부터
        // 받아서  Member 객체를 diary의  member에 넣어줘야 한다.
        // 그러면 member안의 PK값인 id가  diary 테이블에 member_id에 저장
        Member member = memberRepository.findByEmail(email);
        diary.setMember(member);

        diaryRepository.save(diary);
    }

}
