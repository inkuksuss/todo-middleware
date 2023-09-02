package com.project.todo;

import com.project.todo.config.security.provider.JwtTokenProvider;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;


@EnableJpaAuditing
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class TodoApplication {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}



	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void initData() {
		Member testMember = new Member("test1", "test@naver.com", jwtTokenProvider.encryptPassword("1111"), MEMBER_TYPE.MEMBER);
		memberRepository.save(testMember);

	}
}