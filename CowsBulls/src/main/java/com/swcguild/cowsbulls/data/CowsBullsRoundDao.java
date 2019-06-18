/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.cowsbulls.data;

import com.swcguild.cowsbulls.models.Round;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface CowsBullsRoundDao {
    
    //create & read rounds
    
    public Round createRound(Round round, int gameId);
    
    public List<Round> getRounds(int gameId);
 
    
    public Round getRoundById(int roundId);
    
}
