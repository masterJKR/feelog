package com.feelog.service;

import com.feelog.Dto.CommentDto;
import com.feelog.Dto.CommentRequestDto;
import com.feelog.Dto.CommentResponseDto;
import com.feelog.Entity.Comment;
import com.feelog.Entity.Diary;
import com.feelog.Entity.Member;
import com.feelog.repository.CommentRepository;
import com.feelog.repository.DiaryRepository;
import com.feelog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    public CommentResponseDto write(CommentRequestDto commentRequestDto, String email) {
        // 어떤일기에 댓글이냐?  일기 객체 가져오기
        Diary diary = diaryRepository.findById(commentRequestDto.getDiaryId()).get();
        // 누가 작성한 댓글?  멤버 객체 가져오기
        Member member = memberRepository.findByEmail(email);

        Comment comment = new Comment();
        comment.setDiary(diary);
        comment.setMember(member);
        comment.setContent(commentRequestDto.getContent());
        commentRepository.save(comment);
        ///  여기 까지하면  테이블에 댓글 저장은 끝

        ///  응답으로 화면 출력을 위해 내용 보내기
        CommentResponseDto responseDto =new CommentResponseDto();
        responseDto.setContent(commentRequestDto.getContent());
        responseDto.setId(comment.getId());
        responseDto.setWriterName(member.getName());
        responseDto.setCreatedAt(comment.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
        return  responseDto;
    }

    public List<CommentDto> getAll(Long id, String email) {
        List<CommentDto> commentDtoList =  new ArrayList<>();

        Member member = memberRepository.findByEmail(email);

        // 현재 일기 PK에 해당하는 모든 댓글 가져오기
        List<Comment> comments = commentRepository.findByDiaryIdOrderByIdDesc( id );
        for (Comment comment : comments) {
            CommentDto commentDto = new CommentDto();
            commentDto.setContent(comment.getContent());
            commentDto.setId(comment.getId());
            commentDto.setWriterName(comment.getMember().getName());
            commentDto.setCreatedAt(comment.getCreatedAt());
            commentDto.setWriterId(comment.getMember().getId());
            commentDto.setMyComment( member.getId() == comment.getMember().getId()  );
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
}
/*
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");


 */