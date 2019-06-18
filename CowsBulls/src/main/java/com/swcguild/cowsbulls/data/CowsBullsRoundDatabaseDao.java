/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.cowsbulls.data;

import com.swcguild.cowsbulls.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ctrop
 */

@Repository  // makes the class an injectable dependency
public class CowsBullsRoundDatabaseDao implements CowsBullsRoundDao{
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CowsBullsRoundDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    

    @Override
    @Transactional
    public Round createRound(Round round, int gameId) {
     Timestamp ts= Timestamp.valueOf(round.getCurrentTime());
     final String sql= "INSERT INTO Round(gameId, roundNum, currentTime, exactMatches, partialMatches, guess) VALUES (?,?,?,?,?,?)";
     jdbcTemplate.update(sql, gameId, round.getRoundNum(), ts, round.getExactMatches(), round.getPartialMatches(), round.getGuess());
     
     int newId= jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
     

     round.setRoundId(newId);
     return round;

    }

    @Override
    public List<Round> getRounds(int gameId) {
         final String sql= "SELECT * FROM Round WHERE gameId =? ORDER BY currentTime desc";
         List<Round> gameRounds= jdbcTemplate.query(sql, new CowsBullsRoundJDBCMapper(), gameId);
         return gameRounds;
    }

    @Override
    public Round getRoundById(int roundId) {
      
       final String sql = "SELECT * FROM Round WHERE id = ?;";
       Round r= jdbcTemplate.queryForObject(sql, new CowsBullsRoundJDBCMapper(), roundId);
       return r;
    }
    
    private static final class CowsBullsRoundJDBCMapper implements RowMapper<Round>{

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            LocalDateTime time= rs.getTimestamp("currentTime").toLocalDateTime();
           Round r = new Round();
           
           r.setRoundId(rs.getInt("id"));
           r.setRoundNum(rs.getInt("roundNum"));
           r.setCurrentTime(time);
           r.setExactMatches(rs.getInt("exactMatches"));
           r.setPartialMatches(rs.getInt("partialMatches"));
           r.setGuess(rs.getInt("guess"));
           
           return r;
           
        }
    
}
    
    
}
