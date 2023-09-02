package com.project.todo.controller;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.condition.MemberSearchCond;
import com.project.todo.domain.dto.PageDto;
import com.project.todo.domain.model.member.MemberPrincipal;
import com.project.todo.domain.request.member.JoinRequest;
import com.project.todo.domain.request.member.LoginRequest;
import com.project.todo.domain.request.member.MemberSearchRequest;
import com.project.todo.domain.response.MemberDetailResponse;
import com.project.todo.domain.response.common.ResponsePageResult;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.domain.types.RESPONSE_CODE;
import com.project.todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 헤더 토큰을 통해 사용자 정보를 조회
     *
     * @return MemberDetailResponse
     */

    @GetMapping("/info")
    public ResponseEntity<ResponseResult<MemberDetailResponse>> getMemberInfo (Authentication authentication) {
        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();
        MemberDto member = principal.getMember();

        MemberDetailResponse response = new MemberDetailResponse();
        response.setId(member.getId());
        response.setName(member.getName());
        response.setEmail(member.getEmail());
        response.setType(member.getType());
        response.setCreated(member.getCreated());
        response.setUpdated(member.getUpdated());

        return new ResponseEntity<>(new ResponseResult<>(response), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseResult<MemberDetailResponse>> login(@Validated @RequestBody LoginRequest request) {

        MemberDto loginMember = memberService.doLogin(request.getEmail(), request.getPassword());

        // create response
        MemberDetailResponse response = new MemberDetailResponse();
        response.setId(loginMember.getId());
        response.setName(loginMember.getName());
        response.setEmail(loginMember.getEmail());
        response.setType(loginMember.getType());
        response.setCreated(loginMember.getCreated());
        response.setUpdated(loginMember.getUpdated());
        response.addToken(loginMember.getToken());

        return new ResponseEntity<>(new ResponseResult<>(response), HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseResult<Void>> join(@Validated @RequestBody JoinRequest request) {

        //create join dto
        MemberDto memberDto = new MemberDto();
        memberDto.setName(request.getName());
        memberDto.setEmail(request.getEmail());
        memberDto.setPassword(request.getPassword());

        memberService.doJoin(memberDto);

        return new ResponseEntity<>(new ResponseResult<>(), HttpStatus.OK);
    }

    @PostMapping("/members")
    public ResponseEntity<ResponsePageResult<MemberDto>> getMemberList(@Validated @RequestBody MemberSearchRequest request) {

        MemberSearchCond memberSearchCond = new MemberSearchCond();
        memberSearchCond.setName(request.getName());
        memberSearchCond.setEmail(request.getEmail());
        memberSearchCond.setPage(request.getPage());
        memberSearchCond.setSize(request.getSize());


        PageDto<MemberDto> pageDto = memberService.searchMemberList(memberSearchCond);

        return new ResponseEntity<>(
                new ResponsePageResult<>(pageDto),
                HttpStatus.OK
        );
    }
}
