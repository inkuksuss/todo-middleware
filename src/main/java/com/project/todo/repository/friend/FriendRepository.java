package com.project.todo.repository.friend;

import com.project.todo.domain.entity.Friend;
import com.project.todo.domain.types.FRIEND_TYPE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FriendRepository extends CrudRepository<Friend, Long>, FriendRepositoryCustom {

    @Query("select f from Friend f " +
            "where (f.firstMember.id = :firstId or f.secondMember.id = :firstId) and " +
            "(f.firstMember.id = :secondId or f.secondMember.id = :secondId) and " +
            "f.isDelete = 'N'")
    Optional<Friend> findFriendRelationShip(Long firstId, Long secondId);

    @Query("select f from Friend f " +
            "where (f.firstMember.id = :firstId or f.secondMember.id = :firstId) and " +
            "(f.firstMember.id = :secondId or f.secondMember.id = :secondId) and " +
            "f.type = 'COMPLETE' and " +
            "f.isDelete = 'N'")
    Optional<Friend> checkIsFriend(Long firstId, Long secondId);
}
