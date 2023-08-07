package com.project.todo.controller;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.request.MemberDetailRequest;
import com.project.todo.domain.response.MemberDetailResponse;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/login")
    public ResponseEntity<ResponseResult<MemberDetailResponse>> login(@Validated @RequestBody MemberDetailRequest request) {

        log.info("Request = {}", request.toString());

        //create join dto
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(request.getEmail());
        memberDto.setPassword(request.getPassword());

        MemberDto loginMember = memberService.doLogin(memberDto);

        // create response
        MemberDetailResponse response = new MemberDetailResponse();
        response.setId(loginMember.getId());
        response.setName(loginMember.getName());
        response.setEmail(loginMember.getEmail());
        response.setCreated(loginMember.getCreated());
        response.setUpdated(loginMember.getUpdated());

        return new ResponseEntity<>(new ResponseResult<>(response), HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseResult<Void>> join(@Validated @RequestBody MemberDetailRequest request) {

        //create join dto
        MemberDto memberDto = new MemberDto();
        memberDto.setName(request.getName());
        memberDto.setEmail(request.getEmail());
        memberDto.setPassword(request.getPassword());

        memberService.doJoin(memberDto);

        return new ResponseEntity<>(new ResponseResult<>(), HttpStatus.OK);
    }
}
