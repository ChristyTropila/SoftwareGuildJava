/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.cowsbulls.models;

import java.time.LocalDateTime;

/**
 *
 * @author ctrop
 */
public class Round {
    
    public int roundId;
    public int roundNum;
    public int guess;
    public int exactMatches =0; //start the count off at 0
    public int partialMatches =0; // start the count off at 0
    
    LocalDateTime currentTime;
    
    
    
    //each round is to dispaly the guess and time of the guess.
    public Round (int guess, int round){
        this.guess = guess;
        this.roundNum= round;
        this.currentTime= currentTime.now();
    }
    
    public Round(){
        
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public int getExactMatches() {
        return exactMatches;
    }

    public void setExactMatches(int exactMatches) {
        this.exactMatches = exactMatches;
    }

    public int getPartialMatches() {
        return partialMatches;
    }

    public void setPartialMatches(int partialMatches) {
        this.partialMatches = partialMatches;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }


    
    
    
    
    
    
    
    
    
    
    
}
