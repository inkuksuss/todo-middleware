package com.project.todo.controller;

;
import com.project.todo.controller.response.member.GetDefaultMemberRes;
import com.project.todo.domain.factory.dtofactory.dto.MemberDto;
import com.project.todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
// TODO 에러처리
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/get-default-member")
    public ResponseEntity<GetDefaultMemberRes> getDefaultMember() {
        MemberDto defaultMember = memberService.findDefaultMember();
        GetDefaultMemberRes memberDto = GetDefaultMemberRes.fromDto(defaultMember);

        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @PostMapping("/get-member")
    public ResponseEntity<?> getMember() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
