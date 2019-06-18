/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.LocationsDao;
import com.sg.superherosighting.dtos.Locations;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ctrop
 */
@Service
public class LocationsServiceDBImpl implements LocationsService{
    
        @Autowired
    LocationsDao dao;

    public LocationsServiceDBImpl(LocationsDao dao) {
        this.dao = dao;
    }
    
    

    @Override
    public void createLocation(Locations location) {
      dao.createLocation(location);
    }

    @Override
    public Locations getLocationById(int locationId) {
       return dao.getLocationById(locationId);
     }

    @Override
    public List<Locations> getAllLocations() {
     return dao.getAllLocations();
    }

    @Override
    public void updateLocation(Locations location) {
     dao.updateLocation(location);
    }

    @Override
    public void deleteLocation(int locationId) {
       dao.deleteLocation(locationId);
    }
    
}
