package com.epam.springadvanced.repository.impl;

import com.epam.springadvanced.entity.UserAccount;
import com.epam.springadvanced.repository.AccountRepository;
import com.epam.springadvanced.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepositoryImpl implements AccountRepository{
    private static final String UPDATE_ACCOUNT = "UPDATE account SET user_id=?, amount=? WHERE id=?";
    private static final String DELETE_ACCOUNT = "DELETE FROM account WHERE user_id=?";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM account WHERE user_id=?";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserRepository userRepository;

    @Override
    public UserAccount getByUserId(long userId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_USER_ID, accountMapper(), userId);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return null;
    }

    @Override
    public UserAccount save(UserAccount account) {
        if (account != null) {
            if (account.getId() == null) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("account");
                insert.setGeneratedKeyName("id");
                Map<String, Object> args = new HashMap<>();
                args.put("amount", account.getAmount());
                args.put("user_id", account.getUser().getId());
                account.setId(insert.executeAndReturnKey(args).longValue());
            } else {
                jdbcTemplate.update(UPDATE_ACCOUNT,
                        account.getUser().getId(),
                        account.getAmount(),
                        account.getId());
            }
        }
        return account;
    }

    @Override
    public void delete(long userId) {
        jdbcTemplate.update(DELETE_ACCOUNT, userId);
    }

    private RowMapper<UserAccount> accountMapper() {
        return (rs, rowNum) -> {
            UserAccount account = new UserAccount();
            account.setId(rs.getLong(1));
            account.setUser(userRepository.findById(rs.getLong(2)));
            account.setAmount(rs.getFloat(3));
            return account;
        };
    }
}
