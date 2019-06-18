/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.cowsbulls.controllers;

import com.swcguild.cowsbulls.models.Game;
import com.swcguild.cowsbulls.models.Round;
import com.swcguild.cowsbulls.models.UserGuess;
import com.swcguild.cowsbulls.service.CowsBullsGameService;
import com.swcguild.cowsbulls.service.CowsBullsRoundService;
import java.util.List;
import static javafx.scene.input.KeyCode.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ctrop
 */
@RestController // tells spring to scan for methods that can handle HTTP requests and convert method results to JSON
@RequestMapping("/api/cowsbulls")
public class CowsBullsController {
    
    @Autowired
    CowsBullsRoundService service;
    
    @Autowired
    CowsBullsGameService gService;

    public CowsBullsController(CowsBullsRoundService service, CowsBullsGameService gService) {
        this.service = service;
        this.gService = gService;
    }
    
    
    // GET, PUT, POST, DELETE - HTTP
    // READ, Update, Create, Delete - DAO
    // Select, Update, Insert, Delete - SQL@
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(){
      Game g= gService.create();
      return g;
    }
    
    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public Round createRound(@RequestBody UserGuess guess){
      
        Round r = service.createRound(guess);
        return r;
    }
    
    
    
   
    @GetMapping("/game") // GET requests
    public List<Game> all(){
        List<Game> g = gService.getAllGames();
        if(gService.getAllGames().isEmpty()){
            return null;
    }
        for(Game games: g){
          if(!games.getGameStatus()){
              games.setAnswer(0);
          }
        }
         
        return g;
    }
    
    @GetMapping("/game/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int id){
     
     Game g= gService.getGameById(id);
      
     if(!g.getGameStatus()){
         g.setAnswer(0);
     }
 
     if(g==null){
         return new ResponseEntity(null, HttpStatus.NOT_FOUND);
     }
     return ResponseEntity.ok(g);
    }
    
    
     @GetMapping("/round/{gameId}")
     public List <Round>getRoundById(@PathVariable int gameId){
         List<Round> r=service.getRounds(gameId);
    
         return r;
   
}
}
