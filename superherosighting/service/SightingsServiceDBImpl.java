/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.SightingsDao;
import com.sg.superherosighting.dtos.Sightings;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ctrop
 */
@Service
public class SightingsServiceDBImpl implements SightingsService{
    
        @Autowired
    SightingsDao dao;

    public SightingsServiceDBImpl(SightingsDao dao) {
        this.dao = dao;
    }
    
    

    @Override
    public void addSighting(Sightings sighting) {
      dao.addSighting(sighting);
    }

    @Override
    public Sightings viewSighting(int sightingId) {
     return dao.viewSighting(sightingId);
    }

    @Override
    public List<Sightings> viewAllSighting() {
      return dao.viewAllSighting();
    }

    @Override
    public List<Sightings> viewAllSightingsByDate(LocalDate date) {
      return dao.viewAllSightingsByDate(date);
    }

    @Override
    public List<Sightings> getSightingByLoc(int locationId) {
       return dao.getSightingByLoc(locationId);
    }

    @Override
    public List<Sightings> getSightingsByVillain(int villainID) {
        return dao.getSightingsByVillain(villainID);
    }

    @Override
    public void updateSighting(Sightings sighting) {
     dao.updateSighting(sighting);
    }

    @Override
    public void deleteSighting(int sightingId) {
        dao.deleteSighting(sightingId);
    }

    @Override
    public List<Sightings> showLastTenSightings() {
       return dao.showLastTenSightings();
    }
    
}
