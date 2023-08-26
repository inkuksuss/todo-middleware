package com.project.todo.controller;

import com.project.todo.config.argument_resolver.annotation.LoginId;
import com.project.todo.domain.dto.UpdateFriendDto;
import com.project.todo.domain.request.friend.AddFriendRequest;
import com.project.todo.domain.request.friend.UpdateFriendRequest;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;

    /**
    * @param request the request form for add friend
    * @param memberId login member id
     *
    * @return Long void
     *
    * @throws IllegalStateException when duplicate friend request or no existed member id
    * @throws IllegalArgumentException when sender id or receiver id is null
    */
    @PostMapping("/save")
    public ResponseEntity<ResponseResult<Long>> addFriend(
            @RequestBody @Validated AddFriendRequest request,
            @LoginId Long memberId
    ) {
        friendService.addFriend(memberId, request.getTargetId(), request.getFriendType());

        return new ResponseEntity<>(new ResponseResult<>(), HttpStatus.OK);
    }

    /**
     * @param request the request form for update friend
     * @param friendId update friend id
     * @param memberId login member id
     *
     * @return void
     *
     * @throws NoSuchElementException when cannot find friend relationship
     * @throws IllegalArgumentException when request state is not support
     * @throws IllegalStateException when modifier and sender is same or invalid friend state
     */
    @PatchMapping("/{friendId}")
    public ResponseEntity<ResponseResult<Long>> updateFriend(
            @RequestBody @Validated UpdateFriendRequest request,
            @PathVariable Long friendId,
            @LoginId Long memberId
    ) {
        UpdateFriendDto updateFriendDto = new UpdateFriendDto();
        updateFriendDto.setModifierId(memberId);
        updateFriendDto.setFriendId(friendId);
        updateFriendDto.setRequestType(request.getRequestState());

        friendService.updateFriendRelationShip(updateFriendDto);

        return new ResponseEntity<>(new ResponseResult<>(), HttpStatus.OK);
    }


    /**
     *
     * @param friendId delete friend id
     * @param memberId login member id
     *
     * @return void
     *
     * @throws NoSuchElementException if not found todo to delete
     * @throws IllegalArgumentException when modifier id or friend id is null
     */
    @DeleteMapping("/{friendId}")
    public ResponseEntity<ResponseResult<Void>> deleteFriend(
            @PathVariable Long friendId,
            @LoginId Long memberId
    ) {
        friendService.removeFriend(memberId, friendId);

        return new ResponseEntity<>(new ResponseResult<>(), HttpStatus.OK);
    }
}
