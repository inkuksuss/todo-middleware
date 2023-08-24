package com.project.todo.service;

import com.project.todo.domain.condition.FriendSearchCond;
import com.project.todo.domain.dto.FriendDetailDto;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.dto.PageDto;
import com.project.todo.domain.dto.UpdateFriendDto;
import com.project.todo.domain.entity.Friend;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import com.project.todo.repository.friend.FriendRepository;
import com.project.todo.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class FriendServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired FriendService friendService;
    @Autowired
    FriendRepository friendRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void beforeEach() {
        for (int i = 0; i < 20; i++) {
            MemberDto memberDto = new MemberDto();
            memberDto.setName("test" + i);
            memberDto.setPassword("111");
            memberDto.setEmail("test@naver.com" + i);
            memberService.doJoin(memberDto);
        }
    }
    
    @Test
    @DisplayName("success")
    void addSuccess() {
        // given
        Optional<Member> test1 = memberRepository.findByName("test1");
        Optional<Member> test2 = memberRepository.findByName("test2");

        // when
        friendService.addFriend(test1.get().getId(), test2.get().getId(), null);
        
        // then
        Friend friendRelationShip = friendRepository.findFriendRelationShip(test1.get().getId(), test2.get().getId()).get();
        assertThat(friendRelationShip.getSenderId()).isEqualTo(test1.get().getId());
        assertThat(friendRelationShip.getFirstMember().getId()).isEqualTo(test1.get().getId());
        assertThat(friendRelationShip.getSecondMember().getId()).isEqualTo(test2.get().getId());
        assertThat(friendRelationShip.getState()).isEqualTo(REQUEST_STATE.PENDING);
        assertThat(friendRelationShip.getType()).isEqualTo(FRIEND_TYPE.COMMON);
    }

    @Test
    @DisplayName("이미 친구 기록이 있을 때")
    void addExistedSuccess() {
        // given
        Optional<Member> test1 = memberRepository.findByName("test1");
        Optional<Member> test2 = memberRepository.findByName("test2");
        friendService.addFriend(test1.get().getId(), test2.get().getId(), null);
        Friend friendRelationShip = friendRepository.findFriendRelationShip(test1.get().getId(), test2.get().getId()).get();

        // when
        friendRelationShip.remove();
        em.persist(friendRelationShip);
        friendService.addFriend(test1.get().getId(), test2.get().getId(), null);

        // then
        Friend friendRelationShip2 = friendRepository.findFriendRelationShip(test1.get().getId(), test2.get().getId()).get();
        assertThat(friendRelationShip2.getSenderId()).isEqualTo(test1.get().getId());
        assertThat(friendRelationShip2.getFirstMember().getId()).isEqualTo(test1.get().getId());
        assertThat(friendRelationShip2.getSecondMember().getId()).isEqualTo(test2.get().getId());
        assertThat(friendRelationShip2.getState()).isEqualTo(REQUEST_STATE.PENDING);
        assertThat(friendRelationShip2.getType()).isEqualTo(FRIEND_TYPE.COMMON);
    }

    @Test
    @DisplayName("친구 신청한 사용자가 존재하지 않을 때")
    void invalidMember() {
        // given
        Optional<Member> test1 = memberRepository.findByName("test1");
        Optional<Member> test2 = memberRepository.findByName("test2");
        friendService.addFriend(test1.get().getId(), test2.get().getId(), null);


        // when
        em.remove(test2.get());
        em.flush();
        em.clear();

        assertThatThrownBy(() -> friendService.addFriend(test1.get().getId(), test2.get().getId(), null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("이미 존재하는 친구가 pending or complete 일때")
    void existMemberPending() {
        // given
        Optional<Member> test1 = memberRepository.findByName("test1");
        Optional<Member> test2 = memberRepository.findByName("test2");
        friendService.addFriend(test1.get().getId(), test2.get().getId(), null);
        Friend friendRelationShip1 = friendRepository.findFriendRelationShip(test1.get().getId(), test2.get().getId()).get();


        // when
        friendRelationShip1.accept();
        em.persist(friendRelationShip1);
        em.flush();
        em.clear();

        assertThatThrownBy(() -> friendService.addFriend(test1.get().getId(), test2.get().getId(), null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("친구 요청 수락")
    void acceptRequest() {
        // given
        Member test1 = memberRepository.findByName("test1").get();
        Member test2 = memberRepository.findByName("test2").get();
        friendService.addFriend(test1.getId(), test2.getId(), null);

        // when
        UpdateFriendDto updateFriendDto = new UpdateFriendDto();
        updateFriendDto.setModifierId(test2.getId());
        updateFriendDto.setTargetId(test1.getId());
        updateFriendDto.setRequestType(REQUEST_STATE.COMPLETE);
        friendService.updateFriendRelationShip(updateFriendDto);
        // then
        Friend friendRelationShip = friendRepository.findFriendRelationShip(test1.getId(), test2.getId()).get();
        assertThat(friendRelationShip.getState()).isEqualTo(REQUEST_STATE.COMPLETE);
    }

    @Test
    @DisplayName("친구 요청 거절")
    void refuseRequest() {
        // given
        Member test1 = memberRepository.findByName("test1").get();
        Member test2 = memberRepository.findByName("test2").get();
        friendService.addFriend(test1.getId(), test2.getId(), null);

        // when
        UpdateFriendDto updateFriendDto = new UpdateFriendDto();
        updateFriendDto.setModifierId(test2.getId());
        updateFriendDto.setTargetId(test1.getId());
        updateFriendDto.setRequestType(REQUEST_STATE.REFUSE);
        friendService.updateFriendRelationShip(updateFriendDto);
        // then
        Friend friendRelationShip = friendRepository.findFriendRelationShip(test1.getId(), test2.getId()).get();
        assertThat(friendRelationShip.getState()).isEqualTo(REQUEST_STATE.REFUSE);
    }

    @Test
    @DisplayName("친구 관계 올바르지 않을 때")
    void removeInvalidFriend() {
        // given
        Member test1 = memberRepository.findByName("test1").get();
        Member test2 = memberRepository.findByName("test2").get();
        friendService.addFriend(test1.getId(), test2.getId(), null);
        // when

        // then
        assertThatThrownBy(() -> friendService.removeFriend(test1.getId(), test2.getId(), null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("친구 삭제")
    void removeFriend() {
        // given
        Member test1 = memberRepository.findByName("test1").get();
        Member test2 = memberRepository.findByName("test2").get();
        friendService.addFriend(test1.getId(), test2.getId(), null);
        UpdateFriendDto updateFriendDto = new UpdateFriendDto();
        updateFriendDto.setModifierId(test2.getId());
        updateFriendDto.setTargetId(test1.getId());
        updateFriendDto.setRequestType(REQUEST_STATE.COMPLETE);
        friendService.updateFriendRelationShip(updateFriendDto);

        // when
        friendService.removeFriend(test1.getId(), test2.getId(), null);
        // then
        Friend friendRelationShip = friendRepository.findFriendRelationShip(test1.getId(), test2.getId()).get();
        assertThat(friendRelationShip.getState()).isEqualTo(REQUEST_STATE.DESTROY);
    }

    @Test
    @DisplayName("내 친구 조회")
    void getMyFriend() {
        // given
        Member me = null;
        for (int i = 2; i < 12; i++) {
            Member test1 = memberRepository.findByName("test1").get();
            Member test2 = memberRepository.findByName("test" + i).get();
            friendService.addFriend(test1.getId(), test2.getId(), null);
            UpdateFriendDto updateFriendDto = new UpdateFriendDto();
            updateFriendDto.setModifierId(test2.getId());
            updateFriendDto.setTargetId(test1.getId());
            updateFriendDto.setRequestType(REQUEST_STATE.COMPLETE);
            friendService.updateFriendRelationShip(updateFriendDto);
            me = test1;
        }

        for (int i = 4; i < 14; i++) {
            Member test3 = memberRepository.findByName("test3").get();
            Member test4 = memberRepository.findByName("test" + i).get();
            friendService.addFriend(test3.getId(), test4.getId(), null);
            UpdateFriendDto updateFriendDto = new UpdateFriendDto();
            updateFriendDto.setModifierId(test4.getId());
            updateFriendDto.setTargetId(test3.getId());
            updateFriendDto.setRequestType(REQUEST_STATE.COMPLETE);
            friendService.updateFriendRelationShip(updateFriendDto);
        }

        // when
        FriendSearchCond friendSearchCond = new FriendSearchCond();
        friendSearchCond.setTargetId(me.getId());
        PageDto<FriendDetailDto> friendList = friendService.getFriendList(friendSearchCond);
        // then
        assertThat(friendList.getDataList().size()).isEqualTo(10);
        assertThat(friendList.getDataList().stream().map(FriendDetailDto::getMemberName)).contains("test1");
    }

}