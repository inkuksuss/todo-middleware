package com.project.todo.controller;

import com.project.todo.config.argument_resolver.annotation.LoginId;
import com.project.todo.repository.condition.FriendSearchCond;
import com.project.todo.service.dto.friend.FriendDetailDto;
import com.project.todo.service.dto.PageDto;
import com.project.todo.service.dto.friend.UpdateFriendDto;
import com.project.todo.controller.request.friend.AddFriendRequest;
import com.project.todo.controller.request.friend.FriendSearchRequest;
import com.project.todo.controller.request.friend.UpdateFriendRequest;
import com.project.todo.controller.response.common.ResponsePageResult;
import com.project.todo.controller.response.common.ResponseResult;
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
    @PostMapping("/update/{friendId}")
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
    @PostMapping("/delete/{friendId}")
    public ResponseEntity<ResponseResult<Void>> deleteFriend(
            @PathVariable Long friendId,
            @LoginId Long memberId
    ) {
        friendService.removeFriend(memberId, friendId);

        return new ResponseEntity<>(new ResponseResult<>(), HttpStatus.OK);
    }

    /**
     *
     * @param request get friend list with paging
     *
     * @return ResponsePageResult<FriendDetailDto>
     *
     * @throws NoSuchElementException if not found todo to delete
     * @throws IllegalArgumentException when modifier id or friend id is null
     */
    @GetMapping("/{targetId}")
    public ResponseEntity<ResponsePageResult<FriendDetailDto>> getFriendList(
            @RequestBody FriendSearchRequest request,
            @PathVariable Long targetId
    ) {
        FriendSearchCond cond = new FriendSearchCond();
        cond.setTargetId(targetId);
        cond.setSenderId(request.getSenderId());
        cond.setFriendName(request.getFriendName());
        cond.setFriendEmail(request.getFriendEmail());
        cond.setRequestState(request.getRequestState());
        cond.setPage(request.getPage());
        cond.setSize(request.getSize());

        PageDto<FriendDetailDto> friendList = friendService.getFriendList(cond);

        return new ResponseEntity<>(new ResponsePageResult<>(friendList), HttpStatus.OK);
    }
}
