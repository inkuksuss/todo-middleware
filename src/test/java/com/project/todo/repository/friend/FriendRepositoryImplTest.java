package com.project.todo.repository.friend;

import com.project.todo.domain.dto.FriendSimpleDynamicDto;
import com.project.todo.domain.entity.Friend;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@SpringBootTest
@Transactional
class FriendRepositoryImplTest {


    @Autowired
    FriendRepository friendRepository;
    @Autowired
    EntityManager em;

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    @DisplayName("findSimpleDynamic() 값 없을 시 에러 터지는지")
    void findSimpleDynamicEx() {
        // given
        FriendSimpleDynamicDto friendSimpleDynamicDto = new FriendSimpleDynamicDto();
        friendSimpleDynamicDto.setFirstMemberId(1L);
        friendSimpleDynamicDto.setSecondMemberId(2L);
        friendSimpleDynamicDto.setSenderId(1L);

        // when
        Optional<Friend> simpleDynamicFriend = friendRepository.findSimpleDynamicFriend(friendSimpleDynamicDto);

        // then
        Assertions.assertThat(simpleDynamicFriend).isEqualTo(Optional.empty());
    }

}