/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.cowsbulls.service;

import com.swcguild.cowsbulls.data.CowsBullsRoundDao;
import com.swcguild.cowsbulls.models.Game;
import com.swcguild.cowsbulls.models.Round;
import com.swcguild.cowsbulls.models.UserGuess;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ctrop
 */
@Service
public class CowsBullsRoundService {

    @Autowired
    CowsBullsGameService service;

    @Autowired
    CowsBullsRoundDao dao;

    //crud methods below
    public Round createRound(UserGuess uG) {
        List<Round> rounds = dao.getRounds(uG.getGameId());
        Game g = service.getGameById(uG.getGameId());
        int roundNum;
        if (rounds.isEmpty()) {
            roundNum = 1;
        } else {
            roundNum = rounds.size() + 1; //counting the amound of rounds and adding one to give overall number of rounds
        }

        Round r = new Round(uG.getGuess(), roundNum);
        Round roundsCheckGuesses = checkGuess(r, g);
        return dao.createRound(roundsCheckGuesses, uG.getGameId());
    }

    public List<Round> getRounds(int gameId) {
        return dao.getRounds(gameId);
    }

    public Round getRoundById(int id) {
        return dao.getRoundById(id);
    }

    public int[] createArray(int num){
        char[] number= Integer.toString(num).toCharArray();
        int[] array= new int[number.length];
        for(int i=0; i< number.length; i++){
            array[i] = number[i];
            
        }
        return array;
    }
    public Round checkGuess(Round round, Game game) {
        if (round.getGuess() == game.getAnswer()) {
            round.exactMatches = 4;
            round.partialMatches = 0;
            service.updateGame(round, game.getGameId());
            return round;
        }
       
        //create the answer arry and the guess array
        
     int [] answer = createArray(game.getAnswer());
     int[] guess = createArray(round.getGuess());
     
     //iterate through each array to see partial and exact matches
     for(int i=0; i<4; i++){
         if(guess[i] == answer[i]){
             round.exactMatches+=1;
             
         }else{
             for(int j=i + 1; j<4; j++){
                 if(guess[i] == answer[j]){
                     round.partialMatches +=1;
                 }
             }
         }
        
     }
     return round;
    }

}
