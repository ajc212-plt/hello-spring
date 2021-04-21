package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional //jpa는 데이터가 들어올때 Transaction안에서 수행해야한다.
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
        회원가입
         */
    public Long join(Member member){
        //같은 이름이 있는 중복 회원x
        validateDuplicateMember(member); // 중복회원검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { //ifPresent -> result에 null이 아니라 이미 값이 있으면 아래 로직이 동작을 함.
          throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    /*
    전체 회원 검색
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
