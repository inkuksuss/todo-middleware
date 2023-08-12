package com.project.todo.repository.member;

import com.project.todo.domain.dto.MemberDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class MemberJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MemberJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<MemberDto> findJdbcById(Long id) {
        String sql = "select member_id, name, email, password, type, created, updated, is_delete from member where member_id = :id";
        try {
            Map<String, Long> param = Map.of("id", id);
            MemberDto memberDto = jdbcTemplate.queryForObject(sql, param, memberRowMapper());

            return Optional.of(memberDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<MemberDto> memberRowMapper() {
        return (rs, rowNum) -> {
            MemberDto memberDto = new MemberDto();
            memberDto.setId(rs.getLong("member_id"));
            memberDto.setName(rs.getString("name"));
            memberDto.setEmail(rs.getString("email"));
            memberDto.setPassword(rs.getString("password"));
            memberDto.setCreated(rs.getTimestamp("created").toLocalDateTime());
            memberDto.setUpdated(rs.getTimestamp("updated").toLocalDateTime());
            memberDto.setIsDelete(rs.getString("is_delete"));

            return memberDto;
        };
    }
}
