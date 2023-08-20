package com.project.todo.repository.friend;

import com.project.todo.domain.dto.FriendSimpleDynamicDto;
import com.project.todo.domain.entity.Friend;

import java.util.Optional;

public interface FriendRepositoryCustom {

    Friend findSimpleDynamicFriend(FriendSimpleDynamicDto dynamicDto);
}
