package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.sql.Array;
import java.util.*;
//Test는 각 매서드마다 의존관계 없이 실행이 되어야함. 저장소나 공용데이터를 지워주는 작업도 해야한다.
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals((name)))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStroe(){ //테스트 케이스를 실행할때 각 테스트 후에 해당 데이터를 비워주는 역할
        store.clear();
    }
}
