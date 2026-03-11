package com.feelog.service;

import com.feelog.Dto.SignupDto;
import com.feelog.Entity.Member;
import com.feelog.constant.Role;
import com.feelog.repository.MemberProfileRepository;
import com.feelog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final ModelMapper modelMapper;


    public void saveData(SignupDto signupDto, PasswordEncoder passwordEncoder) {
        if( !signupDto.getPassword().equals(signupDto.getPasswordConfirm())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        Member member = modelMapper.map( signupDto , Member.class);
        member.setPassword(passwordEncoder.encode( signupDto.getPassword()));
        member.setRole(Role.ROLE_USER);

        memberRepository.save( member );  // save 메서드는 jpa에 구현되어있다.

    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException{
        // 시큐리티 사용시 커스텀 로그인 DB의 데이터로 로그인진행 하기 때문에
        //  지금 이 메서드가 필요하다.
        Member member = memberRepository.findByEmail(username);
        if( member == null){
            throw new UsernameNotFoundException(username);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString().replace("ROLE_", ""))
                .build();
    }

}
/*
        jpa  메서드 이름 짓는 방법
        기본적으로 제공하는 메서드  save ( insert ), delete(delete)
        find (select )
        update 쿼리에 해당하는 메서드는 없다.  entity객체를 수정하면
        자동 update 쿼리 실행

        findAll() : select * from 테이블
        findById()  : select * from 테이블 where id=2
             ->  primary key의 컬럼값으로 조회

        count()  : select count(*) from 테이블
            ->  테이블 데이터(레코드) 갯수 가져오기 (반환은 long 타입)

        단일 데이터 가져오려면  findBy  로시작하는 메서드 이름 짓기
        여러 데이터 가져오려면  findAll 로 시작하는 메서드 이름 짓기

        로그인 계정명으로 조회 하려면  Entity클래스의 로그인 계정으로 사용하는
        변수 이름 을 그대로사용한다.
        findByEmail( String email )
         : select * from Member where email='a@a.com';

        find + By  +  변수명 (변수명의 첫글자는 대문자)

        이름으로 member테이블에서 조회한다.
        findByName

        and 연산자로 조회 하려면??
        board테이블  제목(title) 과  조회수(hit) 로 조회 하려면
        findByTitleAndHit()
        select * from board where title='aa' and hit=20;

        findByTitleAndHitOrWriter()

        like 사용법
        select * from board where title like '%바보%';

        findByTitleLike("%바보%")
        %없이  그냥 동작 되게 하려면
        findByTitleContaining( String title)
        findByTitleStartingWith

        select * from board where hit >= 50;
        findByHitGreaterThanEqual( 50 )

        select * from board where hit <=100
        findByHitLessThanEqual( 100 )

        날짤 비교는  이후 ->  After  ,  이전  -> Before
        findByCreatedAtAfter()


        정렬  order by hit  asc , desc
        findByOrderByHitDesc

        limit
        select * from board order by hit limit 2
        hit 기준으로 오름차순 정렬하고  상위 2개 조회

        findTop2ByOrderByHit()

        select * from board order by hit limit 0, 10
        hit 기준으로  오름차순 정렬하고  0번째 부터 10개 조회
       방법1 . findByOrderByHit( Pageable pageable)

       방법2.  nativeQuery 사용
       @Query( value="select * from board order by hit limit 0, 10",
                nativeQuery=true)
       boardList()

       select * from board where hit between 100 and 400
       findByHitBetween( 100, 400 );

       조인
       member 과 board 테이블 조인  -  연관 관계를 가지고 있다.
       board테이블에  member_id  ( member테이블의 PK id)

       select b.* from board b join member m
       on  b.member_id = m.id where m.username='jj11';

       findByMemberUsername( "jj11" )

 */