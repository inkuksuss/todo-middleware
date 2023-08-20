package com.project.todo.service;

import com.project.todo.domain.dto.FriendSimpleDynamicDto;
import com.project.todo.domain.entity.Friend;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import com.project.todo.repository.friend.FriendRepository;
import com.project.todo.repository.member.MemberRepository;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    public FriendService(MemberRepository memberRepository, FriendRepository friendRepository) {
        this.memberRepository = memberRepository;
        this.friendRepository = friendRepository;
    }

    public void addFriendRelationShip(Long senderId, Long receiverId, @Nullable FRIEND_TYPE friendType) {
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
        Member receiver = getReceiver(receiverId, findMemberList, sender);

        Friend friendRelationShip = Friend.createFriendRelationShip(sender, receiver, friendType);

        Optional<Friend> findFriend = friendRepository.findFriendRelationShip(sender.getId(), receiver.getId());

        if (findFriend.isPresent()) {
            Friend existed = findFriend.get();
            friendRelationShip = existed.reapplyFriendRelationShip(senderId, friendType);
        }

        Friend save = friendRepository.save(friendRelationShip);
        log.info("saved friend = {}", save);
    }

    public void updateFriendRelationShip(Long modifier, Long target, FRIEND_TYPE friendType, REQUEST_STATE requestType) {
        if (modifier == null) {
            throw new IllegalArgumentException("modifier id cannot be null");
        }

        if (target == null) {
            throw new IllegalArgumentException("target id cannot be null");
        }

        if (requestType == null) {
            throw new IllegalArgumentException("request type cannot be null");
        }

        FriendSimpleDynamicDto friendSimpleDynamicDto = new FriendSimpleDynamicDto();
        friendSimpleDynamicDto.setFriendType(friendType == null ? FRIEND_TYPE.COMMON : friendType);
        friendSimpleDynamicDto.setRequestType(REQUEST_STATE.PENDING);

        if (requestType == REQUEST_STATE.COMPLETE) {
            friendSimpleDynamicDto.setFirstMemberId(target);
            friendSimpleDynamicDto.setSecondMemberId(modifier);
            friendSimpleDynamicDto.setSenderId(target);

            Friend findFriend = friendRepository.findSimpleDynamicFriend(friendSimpleDynamicDto);
            findFriend.setState(REQUEST_STATE.COMPLETE);

            friendRepository.save(findFriend);
        }
        else if (requestType == REQUEST_STATE.REFUSE) {
            List<Long> idList = Arrays.asList(modifier, target);
            idList.sort((m, t) -> (int) (m - t));

            friendSimpleDynamicDto.setFirstMemberId(idList.get(0));
            friendSimpleDynamicDto.setSecondMemberId(idList.get(1));

            Friend findFriend = friendRepository.findSimpleDynamicFriend(friendSimpleDynamicDto);
            findFriend.setState(REQUEST_STATE.REFUSE);

            friendRepository.save(findFriend);
        }
        else {
            throw new IllegalArgumentException("invalid request state");
        }
    }

    private Member getReceiver(Long receiverId, List<Member> findMemberList, Member sender) {
        Optional<Member> receiver = findMemberList.stream().filter(m -> receiverId.equals(m.getId())).findAny();
        return receiver.orElseThrow(() -> new IllegalStateException("invalid receiver state"));
    }

    private Member getSender(Long senderId, List<Member> findMemberList) {
        Optional<Member> sender = findMemberList.stream().filter(m -> senderId.equals(m.getId())).findAny();
        return sender.orElseThrow(() -> new IllegalStateException("invalid sender state"));
    }


}
