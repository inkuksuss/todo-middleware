package com.project.todo.repository.friend;

import com.project.todo.domain.condition.FriendSearchCond;
import com.project.todo.domain.dto.FriendDetailDto;
import com.project.todo.domain.dto.FriendSimpleDynamicDto;
import com.project.todo.domain.entity.Friend;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

public interface FriendRepositoryCustom {

    Friend findSimpleDynamicFriend(FriendSimpleDynamicDto dynamicDto);

    Page<FriendDetailDto> findSearchDynamicFriend(FriendSearchCond searchCond, Pageable pageable);
}
