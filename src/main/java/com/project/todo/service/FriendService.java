package com.project.todo.service;

import com.project.todo.domain.condition.FriendSearchCond;
import com.project.todo.domain.dto.*;
import com.project.todo.domain.entity.Friend;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import com.project.todo.repository.friend.FriendRepository;
import com.project.todo.repository.member.MemberRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Validated
@Transactional(readOnly = true)
@Service
public class FriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    public FriendService(MemberRepository memberRepository, FriendRepository friendRepository) {
        this.memberRepository = memberRepository;
        this.friendRepository = friendRepository;
    }

    @Transactional
    public void addFriend(Long senderId, Long receiverId, @Nullable FRIEND_TYPE friendType) {
        if (senderId == null) {
            throw new IllegalArgumentException("sender id cannot be null");
        }

        if (receiverId == null) {
            throw new IllegalArgumentException("receiver id cannot be null");
        }

        List<Member> findMemberList = memberRepository.findByIdIn(List.of(senderId, receiverId));
        if (findMemberList.size() != 2) {
            throw new IllegalStateException("invalid member state");
        }

        Member sender = getSender(senderId, findMemberList);
        Member receiver = getReceiver(receiverId, findMemberList);

        Friend friendRelationShip = Friend.createFriendRelationShip(sender, receiver, friendType);

        Optional<Friend> findFriend = friendRepository.findFriendRelationShip(sender.getId(), receiver.getId());

        if (findFriend.isPresent()) {
            Friend existed = findFriend.get();
            if (existed.getState() == REQUEST_STATE.PENDING || existed.getState() == REQUEST_STATE.COMPLETE) {
                throw new IllegalStateException("already existed friend or duplicate request");
            }

            friendRelationShip = existed.reapplyFriendRelationShip(senderId, friendType);
        }

        friendRepository.save(friendRelationShip);
    }

    @Transactional
    public void updateFriendRelationShip(@Valid UpdateFriendDto updateFriendDto) {
        FriendSimpleDynamicDto friendSimpleDynamicDto = new FriendSimpleDynamicDto();
        friendSimpleDynamicDto.setFriendType(updateFriendDto.getFriendType() == null ? FRIEND_TYPE.COMMON : updateFriendDto.getFriendType());
        friendSimpleDynamicDto.setRequestType(REQUEST_STATE.PENDING);
        friendSimpleDynamicDto.setFirstMemberId(this.getFirstId(updateFriendDto.getTargetId(), updateFriendDto.getModifierId()));
        friendSimpleDynamicDto.setSecondMemberId(this.getSecondId(updateFriendDto.getTargetId(), updateFriendDto.getModifierId()));
        friendSimpleDynamicDto.setSenderId(updateFriendDto.getTargetId());

        Optional<Friend> findOptionalFriend = friendRepository.findSimpleDynamicFriend(friendSimpleDynamicDto);
        Friend findFriend = findOptionalFriend.orElseThrow(() -> { throw new NoSuchElementException("can not find friend"); });


        if (findFriend.getState() != REQUEST_STATE.PENDING) {
            throw new IllegalStateException("not existed request");
        }

        if (!updateFriendDto.getTargetId().equals(findFriend.getSenderId())) {
            throw new IllegalStateException("target id and sender id must be same");
        }

        if (updateFriendDto.getRequestType() == REQUEST_STATE.COMPLETE) {
            findFriend.accept();
        }
        else if (updateFriendDto.getRequestType() == REQUEST_STATE.REFUSE) {
            findFriend.refuse();
        }
        else {
            throw new IllegalArgumentException("invalid request state");
        }

        friendRepository.save(findFriend);
    }

    @Transactional
    public void removeFriend(Long modifier, Long target, @Nullable FRIEND_TYPE friendType) {
        if (modifier == null) {
            throw new IllegalArgumentException("modifier id cannot be null");
        }
        if (target == null) {
            throw new IllegalArgumentException("target id cannot be null");
        }

        FriendSimpleDynamicDto friendSimpleDynamicDto = new FriendSimpleDynamicDto();
        friendSimpleDynamicDto.setFirstMemberId(this.getFirstId(modifier, target));
        friendSimpleDynamicDto.setSecondMemberId(this.getSecondId(modifier, target));
        friendSimpleDynamicDto.setRequestType(REQUEST_STATE.COMPLETE);
        friendSimpleDynamicDto.setFriendType(friendType == null ? FRIEND_TYPE.COMMON : friendType);

        Optional<Friend> findOptionalFriend = friendRepository.findSimpleDynamicFriend(friendSimpleDynamicDto);
        Friend findFriendRelationShip = findOptionalFriend.orElseThrow(() -> { throw new NoSuchElementException("can not find friend"); });

        findFriendRelationShip.remove();

        friendRepository.save(findFriendRelationShip);
    }

    public PageDto<FriendDetailDto> getFriendList(@Valid FriendSearchCond cond) {
        PageRequest pageRequest = PageRequest.of(cond.getPage(), cond.getSize());

        Page<FriendDetailDto> searchResult = friendRepository.findSearchDynamicFriend(cond, pageRequest);

        return new PageDto<>(
                searchResult.getTotalElements(),
                searchResult.getTotalPages(),
                searchResult.hasNext(),
                searchResult.getContent()
        );
    }

    private Member getReceiver(Long receiverId, List<Member> findMemberList) {
        Optional<Member> receiver = findMemberList.stream().filter(m -> receiverId.equals(m.getId())).findAny();
        return receiver.orElseThrow(() -> new IllegalStateException("invalid receiver state"));
    }

    private Member getSender(Long senderId, List<Member> findMemberList) {
        Optional<Member> sender = findMemberList.stream().filter(m -> senderId.equals(m.getId())).findAny();
        return sender.orElseThrow(() -> new IllegalStateException("invalid sender state"));
    }

    private Long getFirstId(Long memberId1, Long memberId2) {
        if (memberId1 > memberId2) {
            return memberId2;
        } else if (memberId2 > memberId1) {
            return memberId1;
        } else {
            throw new IllegalStateException("modifier id is must be different with target id");
        }
    }

    private Long getSecondId(Long memberId1, Long memberId2) {
        Long firstId = this.getFirstId(memberId1, memberId2);
        return firstId.equals(memberId1) ? memberId2 : memberId1;
    }
}
