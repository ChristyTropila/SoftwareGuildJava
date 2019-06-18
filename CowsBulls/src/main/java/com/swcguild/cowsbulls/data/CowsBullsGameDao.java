/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.cowsbulls.data;

import com.swcguild.cowsbulls.models.Game;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface CowsBullsGameDao {
    
    
     //crud 
    
    public Game createGame(Game game);
    
    public List<Game> getGames();
    
    public Game getGameById(int id);
    
    public boolean updateGame(Game game);
    
    
    
}
