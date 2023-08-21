package com.project.todo.domain.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
public class FriendDetailDto {

    private Long friendId;

    private Long memberId;

    private String memberName;

    private String memberEmail;

    private LocalDateTime updated;

//    public FriendDetailDto(Long friendId, Long memberId, String memberName, String memberEmail, LocalDateTime updatedBy) {
//        this.friendId = friendId;
//        this.memberId = memberId;
//        this.memberName = memberName;
//        this.memberEmail = memberEmail;
//        this.updatedBy = updatedBy;
//    }
}
