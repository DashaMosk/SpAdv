package com.epam.springadvanced.repository.impl;

import com.epam.springadvanced.entity.Role;
import com.epam.springadvanced.entity.Token;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.AccountRepository;
import com.epam.springadvanced.repository.UserRepository;
import com.epam.springadvanced.repository.WinsRepository;
import com.epam.springadvanced.service.Roles;
import com.epam.springadvanced.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

import static java.util.Optional.ofNullable;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String UPDATE_USER_BY_ID = "UPDATE user SET name=?, email=?, birthDay=?, password=? WHERE id=?";
    private static final String UPDATE_USER_BY_NAME = "UPDATE user SET name=?, email=?, birthDay=?, password=? WHERE name=?";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM user WHERE id=?";
    private static final String SELECT_BY_USER_EMAIL = "SELECT * FROM user WHERE email=?";
    private static final String SELECT_BY_USER_NAME = "SELECT * FROM user WHERE name=?";
    private static final String DELETE_TICKETS = "DELETE FROM tickets WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    private static final String DELETE_USER_ROLE = "DELETE FROM roles WHERE user_id=?";
    private static final String SELECT_ALL = "SELECT * FROM user";
    private static final String SELECT_USER_ROLES =
            "select * from role r\n" +
                    "join roles rs on rs.role_id = r.id\n" +
                    "where user_id=?";
    private static final String SELECT_ROLE_BY_NAME = "select * from role where name = ?";
    private static final String SELECT_TOKENS = "select * from persistent_logins";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private WinsRepository winsRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public User save(User user) {
        if (user != null) {
            User updatedUser = null;
            if (user.getId() != null) {
                // try update users by id
                jdbcTemplate.update(UPDATE_USER_BY_ID,
                        user.getName(),
                        user.getEmail(),
                        Convert.toTimestamp(user.getBirthday()),
                        user.getPassword(),
                        user.getId());
                updatedUser = findById(user.getId());
            } else if (user.getName() != null && !user.getName().isEmpty()) {
                // try update users by name
                jdbcTemplate.update(UPDATE_USER_BY_NAME,
                        user.getName(),
                        user.getEmail(),
                        Convert.toTimestamp(user.getBirthday()),
                        user.getPassword(),
                        user.getName());
                updatedUser = findByName(user.getName());
            }

            if (updatedUser == null) {
                // insert if users not saved yet
                SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user");
                insert.setGeneratedKeyName("id");
                Map<String, Object> args = new HashMap<>();
                args.put("name", user.getName());
                args.put("email", user.getEmail());
                args.put("birthDay", Convert.toTimestamp(user.getBirthday()));
                Md5PasswordEncoder encoder = new Md5PasswordEncoder();
                args.put("password", encoder.encodePassword(user.getPassword(), user.getName()));
                user.setId(insert.executeAndReturnKey(args).longValue());
            } else {
                user = updatedUser;
            }
            if(user.getRoles() == null) {
                user.setRoles(new LinkedList<>());
            }
            if(!hasRegisteredUserRole(user.getRoles())) {
                user.getRoles().add(new Role(getRoleByName(Roles.REGISTERED_USER.name()).getId(), Roles.REGISTERED_USER));
            }
            updateRoles(user.getId(), user.getRoles());
        }

        return user;
    }

    private void updateRoles(long userId, List<Role> roles) {
        try {
            jdbcTemplate.update(DELETE_USER_ROLE, userId);
        }catch(Exception ex) {
            System.out.println("Issue with access to data");
        }
        for (Role role : roles) {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles");
            Map<String, Object> args = new HashMap<>();
            args.put("user_id", userId);
            args.put("role_id", getRoleByName(role.getName().name()).getId());
            insert.execute(args);
        }
    }

    private Role getRoleByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ROLE_BY_NAME, roleMapper(), name);
        } catch (EmptyResultDataAccessException ignored) {}
        return null;
    }

    private boolean hasRegisteredUserRole(List<Role> roles) {
        for(Role r : roles) {
            if(r.getName().equals(Roles.REGISTERED_USER)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(long id) {
        winsRepository.delete(id);
        accountRepository.delete(id);
        jdbcTemplate.update(DELETE_TICKETS, id);
        jdbcTemplate.update(DELETE_USER_ROLE, id);
        jdbcTemplate.update(DELETE_USER, id);
    }

    @Override
    public User findById(long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_USER_ID, userMapper(), id);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_USER_EMAIL, userMapper(), email);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return null;
    }

    @Override
    public User findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_USER_NAME, userMapper(), name);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(SELECT_ALL, userMapper());
    }


    private List<Role> getRoles(long userId) {
        return jdbcTemplate.query(SELECT_USER_ROLES, roleMapper(), userId);
    }

    @Override
    public List<Token> getTokens() {
        return jdbcTemplate.query(SELECT_TOKENS, tokenMapper());
    }

    private RowMapper<Role> roleMapper() {
        return (rs, rowNum) -> {
            Role role = new Role();
            role.setId(rs.getInt(1));
            role.setName(Roles.valueOf(rs.getString(2)));
            return role;
        };
    }

    private RowMapper<Token> tokenMapper() {
        return (rs, rowNum) -> {
            Token token = new Token();
            token.setUserName(rs.getString(1));
            token.setSeries(rs.getString(2));
            token.setToken(rs.getString(3));
            return token;
        };
    }

    private RowMapper<User> userMapper() {
        return (rs, rowNum) -> {
            User user = new User(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    ofNullable(rs.getDate(4)).map(Date::toLocalDate).orElseGet(null)
            );
            user.setPassword(rs.getString(5));
            user.setRoles(getRoles(rs.getLong(1)));
            return user;
        };
    }
}
