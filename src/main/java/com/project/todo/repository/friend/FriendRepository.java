package com.project.todo.repository.friend;

import com.project.todo.domain.entity.Friend;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FriendRepository extends CrudRepository<Friend, Long>, FriendRepositoryCustom {

    @Query("select f from Friend f where f.firstMember.id = :senderId and f.secondMember.id = :receiverId and f.isDelete = 'N'")
    Optional<Friend> findFriendRelationShip(Long senderId, Long receiverId);
}
