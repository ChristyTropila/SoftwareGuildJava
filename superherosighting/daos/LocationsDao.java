/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.dtos.Locations;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface LocationsDao {
    
    
    //crud methods
    
    public void createLocation(Locations location);
    public Locations getLocationById(int locationId);
    public List<Locations> getAllLocations();
    public void updateLocation(Locations location);
    public void deleteLocation (int locationId);
    
   
}
