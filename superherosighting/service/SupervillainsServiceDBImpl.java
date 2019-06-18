/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.SupervillainsDao;
import com.sg.superherosighting.dtos.Supervillains;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ctrop
 */
@Service
public class SupervillainsServiceDBImpl implements SuperVillainsService{
    
    @Autowired
    SupervillainsDao dao;

    public SupervillainsServiceDBImpl(SupervillainsDao dao) {
        this.dao = dao;
    }
    
    
    @Override
    public void createVillain(Supervillains villain) {
         dao.createVillain(villain);
    }

    @Override
    public Supervillains getVillainById(int villainID) {
      return dao.getVillainById(villainID);
    }

    @Override
    public List<Supervillains> getAllVillains() {
       return dao.getAllVillains();
    }

    @Override
    public void updateVillain(Supervillains villain) {
       dao.updateVillain(villain);
    }

    @Override
    public void deleteVillain(int villainID) {
      dao.deleteVillain(villainID);
    }

    @Override
    public List<Supervillains> getAllVillainsByOrgan(int organizationId) {
       return dao.getAllVillainsByOrgan(organizationId);
    }

    @Override
    public List<Supervillains> getAllVillainsBySighting(int sightingId) {
        return dao.getAllVillainsBySighting(sightingId);
    }
    
}
