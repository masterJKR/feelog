package com.feelog.service;

import com.feelog.Entity.Follow;
import com.feelog.Entity.Member;
import com.feelog.repository.FollowRepository;
import com.feelog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public boolean followSave(Long wingId, String email) {
        Member wer = memberRepository.findByEmail(email);
        Member wing = memberRepository.findById(wingId).get();

        Follow follow1 = followRepository.findByFollowerIdAndFollowingId(wer.getId(), wingId);
        if( follow1 ==null){
            Follow follow = new Follow();
            follow.setFollowing(wing);
            follow.setFollower(wer);
            followRepository.save(follow);
            return true;
        }else{
            followRepository.delete(follow1);
            return false;
        }
    }
}
