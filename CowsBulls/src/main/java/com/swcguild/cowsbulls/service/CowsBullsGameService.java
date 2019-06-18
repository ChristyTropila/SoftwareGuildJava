/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.cowsbulls.service;

import com.swcguild.cowsbulls.data.CowsBullsGameDao;
import com.swcguild.cowsbulls.models.Game;
import com.swcguild.cowsbulls.models.Round;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author ctrop
 */
@Service
public class CowsBullsGameService {
    
    @Autowired
    CowsBullsGameDao dao;
    
    @Autowired
    CowsBullsRoundService service;
    
    //crud methods below
    
    public Game create(){
        int randNum = getNumber();
        Game g = new Game(randNum);
        return dao.createGame(g);
        
        
    }
    
    public List<Game> getAllGames(){
        return dao.getGames();
    }
    
    
    public Game getGameById(int id){
        Game g= dao.getGameById(id);
       
        return dao.getGameById(id);
    }
    
    
    
    public boolean updateGame(Round round, int gameId){
        boolean needsUpdate = false;
        Game g = dao.getGameById(gameId);
        ResponseEntity response = null;
        if(round.exactMatches == 4){
            g.setGameStatus(false);
            boolean isUpdated = dao.updateGame(g);
            if(isUpdated){
                response = new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        }
        return needsUpdate;
    }
    
    //Generate the random 4 digits with no repeated numbers
    //I will use an array
    
    public int getNumber(){
        int[] array= new int[4];
        Random randomizer = new Random();
        
        //while position 1 is = 0, randomize the first number in the array to a number between 1-9 
        do{
            array[0]= randomizer.nextInt(9);
        }while( array[0]==0);
        
        //while position 2 is equal to the same number as position 1, randomize it
        
        do{
            array[1]= randomizer.nextInt(9);
        }while(array[1]==array[0]);
        
        do{
            array[2]= randomizer.nextInt(9);
        }while (array[2]==array[1] || array[2] == array[0]);
        
        do{
            array[3]= randomizer.nextInt(9);
        }while ( array[3]==array[2]|| array[3]==array[1]|| array[3]==array[0]);
        
        //enhanced for loop to iterate through the numbers and create one 4 digit number
        int n=0;
        for(int num: array){
            n= 10*n + num;
        }
        return n;
    }
    
    //while the game is being played, dont show the answer
    public Game filterGame(Game game){
        if(game.getGameStatus()){
            game.setAnswer(0);
        }
        return game;
    }
    
    public List<Game> filterAll(List<Game> g){
        g.stream().forEach(game -> filterGame(game));
        return g;
    }
    
}

