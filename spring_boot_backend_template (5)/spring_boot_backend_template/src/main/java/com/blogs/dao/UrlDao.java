package com.blogs.dao;

import com.blogs.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UrlDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save a new URL mapping (with optional user ID)
    public void save(Url url) {
        String sql = "INSERT INTO urls (long_url, short_code, user_id) VALUES (?, ?, ?)";
        
        jdbcTemplate.update(sql,
            url.getLongUrl(),
            url.getShortCode(),
            (url.getUserId() != null) ? url.getUserId() : null
        );
    }


    // Check if a short code already exists
    public boolean existsByShortCode(String shortCode) {
        String sql = "SELECT COUNT(*) FROM urls WHERE short_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, shortCode);
        return count != null && count > 0;
    }

    // Retrieve full URL by short code
    public Url findByShortCode(String shortCode) {
        String sql = "SELECT * FROM urls WHERE short_code = ?";
        List<Url> results = jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToUrl(rs), shortCode);
        return results.isEmpty() ? null : results.get(0);
    }

    // Get all URLs by a specific user
    public List<Url> findAllByUserId(int userId) {
        String sql = "SELECT * FROM urls WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToUrl(rs), userId);
    }

    // Helper method to map ResultSet to Url object
    private Url mapRowToUrl(ResultSet rs) throws SQLException {
        Url url = new Url();
        url.setId(rs.getInt("id"));
        url.setLongUrl(rs.getString("long_url"));
        url.setShortCode(rs.getString("short_code"));
        url.setUserId(rs.getInt("user_id"));
        return url;
    }
}
