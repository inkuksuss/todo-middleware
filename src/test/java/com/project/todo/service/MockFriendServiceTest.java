package com.project.todo.service;

import com.project.todo.config.security.provider.JwtTokenProvider;
import com.project.todo.domain.dto.FriendSimpleDynamicDto;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.dto.UpdateFriendDto;
import com.project.todo.domain.entity.Friend;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.repository.friend.FriendRepository;
import com.project.todo.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
class MockFriendServiceTest {

    @InjectMocks
    private FriendService friendService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FriendRepository friendRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void beforeEach() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("add friend success")
    void addFriendSuccess() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);

        List<Member> findMemberList = Arrays.asList(member1, member2);

        when(memberRepository.findByIdIn(anyList())).thenReturn(findMemberList);
        when(friendRepository.findFriendRelationShip(any(Long.class), any(Long.class))).thenReturn(Optional.empty());

        // when
        friendService.addFriend(member1.getId(), member2.getId(), null);

        // then
        verify(friendRepository, times(1)).save(any(Friend.class));
    }

    @Test
    @DisplayName("add friend invalid param")
    void addFriendInvalidParam() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);

        List<Member> findMemberList = Arrays.asList(member1, member2);

        when(memberRepository.findByIdIn(anyList())).thenReturn(findMemberList);
        when(friendRepository.findFriendRelationShip(any(Long.class), any(Long.class))).thenReturn(Optional.empty());


        // when
        // then
        assertThatThrownBy(() -> friendService.addFriend(member1.getId(), null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("find just 1 member")
    void addFriendFiendOneMember() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);

        List<Member> findMemberList = Arrays.asList(member1);

        when(memberRepository.findByIdIn(anyList())).thenReturn(findMemberList);

        // when
        // then
        assertThatThrownBy(() -> friendService.addFriend(member1.getId(), member2.getId(), null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("duplicate friend request")
    void addFriendDuplicateFriend() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Friend duplicate = Friend.createFriendRelationShip(member1, member2, null);

        List<Member> findMemberList = Arrays.asList(member1, member2);

        when(memberRepository.findByIdIn(anyList())).thenReturn(findMemberList);
        when(friendRepository.findFriendRelationShip(any(Long.class), any(Long.class))).thenReturn(Optional.of(duplicate));

        // when
        // then
        assertThatThrownBy(() -> friendService.addFriend(member1.getId(), member2.getId(), null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("failed friend request")
    void addFriendFailedFriend() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Friend duplicate = Friend.createFriendRelationShip(member1, member2, null);
        duplicate.refuse();

        List<Member> findMemberList = Arrays.asList(member1, member2);

        when(memberRepository.findByIdIn(anyList())).thenReturn(findMemberList);
        when(friendRepository.findFriendRelationShip(any(Long.class), any(Long.class))).thenReturn(Optional.of(duplicate));

        // when
        friendService.addFriend(member1.getId(), member2.getId(), null);

        // then
        verify(friendRepository, times(1)).save(any(Friend.class));
    }

    @Test
    @DisplayName("update friend request")
    void updateFriendSuccess() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Friend findFriend = Friend.createFriendRelationShip(member1, member2, null);

        UpdateFriendDto updateFriendDto = new UpdateFriendDto();
        updateFriendDto.setTargetId(member1.getId());
        updateFriendDto.setModifierId(member2.getId());
        updateFriendDto.setFriendType(FRIEND_TYPE.COMMON);
        updateFriendDto.setRequestType(REQUEST_STATE.COMPLETE);

        when(friendRepository.findSimpleDynamicFriend(any(FriendSimpleDynamicDto.class))).thenReturn(findFriend);

        // when
        friendService.updateFriendRelationShip(updateFriendDto);

        // then
        verify(friendRepository, times(1)).save(any(Friend.class));
    }

    @Test
    @DisplayName("update friend invalid state")
    void updateFriendInvalidState() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Friend findFriend = Friend.createFriendRelationShip(member1, member2, null);
        findFriend.refuse();

        UpdateFriendDto updateFriendDto = new UpdateFriendDto();
        updateFriendDto.setTargetId(member1.getId());
        updateFriendDto.setModifierId(member2.getId());
        updateFriendDto.setFriendType(FRIEND_TYPE.COMMON);
        updateFriendDto.setRequestType(REQUEST_STATE.COMPLETE);

        when(friendRepository.findSimpleDynamicFriend(any(FriendSimpleDynamicDto.class))).thenReturn(findFriend);

        // when
        // then
        assertThatThrownBy(() -> friendService.updateFriendRelationShip(updateFriendDto))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("update friend invalid arg state")
    void updateFriendInvalidArgState() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Friend findFriend = Friend.createFriendRelationShip(member1, member2, null);
        findFriend.refuse();

        UpdateFriendDto updateFriendDto = new UpdateFriendDto();
        updateFriendDto.setTargetId(member1.getId());
        updateFriendDto.setModifierId(member2.getId());
        updateFriendDto.setFriendType(FRIEND_TYPE.COMMON);
        updateFriendDto.setRequestType(REQUEST_STATE.DESTROY);

        when(friendRepository.findSimpleDynamicFriend(any(FriendSimpleDynamicDto.class))).thenReturn(findFriend);

        // when
        // then
        assertThatThrownBy(() -> friendService.updateFriendRelationShip(updateFriendDto))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("update friend change first and second")
    void updateFriendChangeOrder() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Friend findFriend = Friend.createFriendRelationShip(member2, member1, null);
        findFriend.refuse();

        UpdateFriendDto updateFriendDto = new UpdateFriendDto();
        updateFriendDto.setTargetId(member1.getId());
        updateFriendDto.setModifierId(member2.getId());
        updateFriendDto.setFriendType(FRIEND_TYPE.COMMON);
        updateFriendDto.setRequestType(REQUEST_STATE.DESTROY);

        when(friendRepository.findSimpleDynamicFriend(any(FriendSimpleDynamicDto.class))).thenReturn(findFriend);

        // when
        // then
        assertThatThrownBy(() -> friendService.updateFriendRelationShip(updateFriendDto))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("remove friend success")
    void removeFriendSuccess() {
        // given
        Member member1 = new Member(1L, "test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Member member2 = new Member(2L, "test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Friend findFriend = Friend.createFriendRelationShip(member1, member2, null);
        findFriend.accept();

        when(friendRepository.findSimpleDynamicFriend(any(FriendSimpleDynamicDto.class))).thenReturn(findFriend);

        // when
        friendService.removeFriend(member2.getId(), member1.getId(), null);

        // then
        verify(friendRepository, times(1)).save(any(Friend.class));
    }
}