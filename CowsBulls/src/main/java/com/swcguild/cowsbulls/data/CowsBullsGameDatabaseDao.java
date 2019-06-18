/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.cowsbulls.data;

import com.swcguild.cowsbulls.models.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ctrop
 */
@Repository  // makes the class an injectable dependency
public class CowsBullsGameDatabaseDao implements CowsBullsGameDao {

    private final JdbcTemplate jdbcTemplate;

    //this ask for spring dependency injection for a jdbc template
    @Autowired
    public CowsBullsGameDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional // every query run against the database in this method is part of one transaction.
    public Game createGame(Game game) {
        final String sql = "INSERT INTO Game(answer, gameStatus) VALUES (?,?);";
        jdbcTemplate.update(sql, game.getAnswer(), game.getGameStatus());

        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        game.setGameId(newId);
        return game;

    }

    @Override
    public List<Game> getGames() {
        List<Game> allGames;

        //Get games with Answers
        final String sqlWA = "SELECT * FROM Game WHERE gameStatus =?;";
        List<Game> gWA = jdbcTemplate.query(sqlWA, new CowsBullsGameJDBCMapper(), false);

        // List games without answers
        final String sqlWOA = "SELECT gameId, answer, gameStatus FROM Game WHERE gameStatus = ?";
        List<Game> gWOA = jdbcTemplate.query(sqlWOA, new CowsBullsGameJDBCMapper(), true);

        allGames = gWA;
        allGames.addAll(gWOA);
        return allGames;
    }

    //surronded by a try/catch in case that ID does not exist
    @Override
    public Game getGameById(int id) {
        try {
            final String sql = "SELECT * FROM Game WHERE gameId = ?;";
            return jdbcTemplate.queryForObject(sql, new CowsBullsGameJDBCMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    //????? not positive on this yet
    @Override
    public boolean updateGame(Game game) {
        boolean update = true;
        final String sql = "UPDATE Game SET gameStatus = ? WHERE gameId = ?;";
        jdbcTemplate.update(sql,game.gameStatus, game.getGameId());
        return update;
    }

    //define the rowmapper to change the resultset directly into the object
    private static final class CowsBullsGameJDBCMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game g = new Game();
            g.setGameId(rs.getInt("gameId"));
            g.setAnswer(rs.getInt("answer"));
            g.setGameStatus(rs.getBoolean("gamestatus"));
            return g;
        }

    }

}
