package com.engeto.genesis.repository;

import com.engeto.genesis.mapper.UserRowMapper;
import com.engeto.genesis.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;


import java.sql.PreparedStatement;

import java.sql.Statement;
import java.util.List;

@Component
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper = new UserRowMapper();
    KeyHolder keyHolder = new GeneratedKeyHolder();


    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public User createUser(User user) {
        String sql = "INSERT INTO user (name, surname, person_id, uuid) VALUES (?, ?, ?, ?)";

        int affectedRows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getPersonId());
            ps.setString(4, user.getUuid());
            return ps;
        }, keyHolder);
        if (affectedRows > 0) {
            Long generatedId = keyHolder.getKey().longValue();
            return getUserById(generatedId);
        } else {
            return null;  //throw new RuntimeException("Failed to create user.");
        }
    }


    public User getUserById(Long id) {
        String sql = "select * from user where user.id = " + id;
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper);
        } catch (EmptyResultDataAccessException e) {
        }
        return null;
    }

    public List<User> getAllUsers() {
        final String sql = "select * from user";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public List<User> getAllUsersDetail() {
        final String sql = "select * from user";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public void updateUserById(Long id, User updatedUser) {
        final String sql = "update user set name = ?, surname = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedUser.getName(), updatedUser.getSurname(), id);
    }


    public void deleteUser(Long id) {
        final String sql = "delete from user where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsByPersonID(String personID) {
        String sql = "SELECT COUNT(*) FROM user WHERE person_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, personID);
        return count != null && count > 0;
    }


}

