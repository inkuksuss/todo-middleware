package com.project.todo.repository.friend;

import com.project.todo.repository.condition.FriendSearchCond;
import com.project.todo.service.dto.friend.FriendDetailDto;
import com.project.todo.service.dto.friend.FriendSimpleDynamicDto;
import com.project.todo.domain.entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FriendRepositoryCustom {

    Optional<Friend> findSimpleDynamicFriend(FriendSimpleDynamicDto dynamicDto);

    Page<FriendDetailDto> findSearchDynamicFriend(FriendSearchCond searchCond, Pageable pageable);
}
