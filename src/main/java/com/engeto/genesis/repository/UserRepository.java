package com.engeto.genesis.repository;

import com.engeto.genesis.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAll() {
        final String sql ="select * from user";
        return jdbcTemplate.query(sql,userRowMapper);
    }
}
