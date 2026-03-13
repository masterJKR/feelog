package com.feelog.service;

import com.feelog.Dto.DiaryDetailDto;
import com.feelog.Dto.DiaryDto;
import com.feelog.Dto.DiaryListDto;
import com.feelog.Entity.Diary;
import com.feelog.Entity.Member;
import com.feelog.constant.Emotion;
import com.feelog.repository.DiaryRepository;
import com.feelog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    
    // 목록 출력할 내용 가져오기
    public Page<DiaryListDto> getDiaryList(String keyword, Emotion emotion, Pageable pageable) {

        List<Diary> diaryList = diaryRepository.searchDiary( keyword, emotion, pageable );

        List<DiaryListDto> diaryListDtos = new ArrayList<>();
        for (Diary diary : diaryList) {
            DiaryListDto dto = modelMapper.map(diary, DiaryListDto.class);
            diaryListDtos.add(dto);
        }
        return new PageImpl<>(diaryListDtos, pageable, diaryRepository.count());

    }


    public void saveDiary(DiaryDto diaryDto , String email){
        Diary diary = modelMapper.map(diaryDto, Diary.class);
        // 누가 작성했는가  현재 로그인한 계정의  객체를 테이블로 부터
        // 받아서  Member 객체를 diary의  member에 넣어줘야 한다.
        // 그러면 member안의 PK값인 id가  diary 테이블에 member_id에 저장
        Member member = memberRepository.findByEmail(email);
        diary.setMember(member);

        diaryRepository.save(diary);
    }


    public DiaryDetailDto getDiary(Long id) {

        Diary diary = diaryRepository.findById( id ).orElse(null);

        DiaryDetailDto diaryDetailDto
                = modelMapper.map(diary, DiaryDetailDto.class);
        diaryDetailDto.setWriterName( diary.getMember().getName()  );  //일기 작성자

        diaryDetailDto.setEmotionLabel( diary.getEmotion().getLabel() );
        diaryDetailDto.setEmotionEmoji( getEmoji( diary.getEmotion() ) );

        return diaryDetailDto;
    }

    private String getEmoji(Emotion emotion) {
        switch (emotion) {
            case  HAPPY: return "😁";
            case SAD: return "😭";
            case FEAR: return "😱";
            case COMFORT: return "🤍";
            case FRUSTRATED: return "🙍";
            case JOY: return "🥹";
            case ANGER: return "😠";
            case DEPRESSED: return "😞";
            case EXCITED: return "💖";
            case LONELY: return "🌙";
            case MISSING: return "💫";
            case GRATEFUL: return "🙏";
            default: return "❕";
        }
    }


}
