/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dtos.Sightings;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface SightingsService {

    public void addSighting(Sightings sighting);

    public Sightings viewSighting(int sightingId);

    public List<Sightings> viewAllSighting();

    public List<Sightings> viewAllSightingsByDate(LocalDate date);

    public List<Sightings> getSightingByLoc(int locationId);

    public List<Sightings> getSightingsByVillain(int villainID);

    public void updateSighting(Sightings sighting);

    public void deleteSighting(int sightingId);

    public List<Sightings> showLastTenSightings();

}
