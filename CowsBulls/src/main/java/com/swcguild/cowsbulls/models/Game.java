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
public class Game {
    
  public int gameId;
   public int answer;
   public boolean gameStatus;
   
   
   //the number entered in by user will be assigned to variable answer
   public Game(int number){
       this.answer= number;
   }
    
   public Game(){
       
   }
   
   //getters and setters

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public boolean getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }
   
   
}
