/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dtos.Supervillains;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface SuperVillainsService {

    public void createVillain(Supervillains villain);

    public Supervillains getVillainById(int villainID);

    public List<Supervillains> getAllVillains();

    public void updateVillain(Supervillains villain);

    public void deleteVillain(int villainID);
    
    
    
    
    public List<Supervillains> getAllVillainsByOrgan(int organizationId);
    public List<Supervillains>getAllVillainsBySighting(int sightingId);
    

}
