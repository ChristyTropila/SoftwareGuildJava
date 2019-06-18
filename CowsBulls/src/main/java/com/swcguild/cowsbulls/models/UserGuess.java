/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.cowsbulls.models;

/**
 *
 * @author ctrop
 */
public class UserGuess {
    
    Integer gameId;
    Integer guess;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getGuess() {
        return guess;
    }

    public void setGuess(Integer guess) {
        this.guess = guess;
    }
    
    
    
}
