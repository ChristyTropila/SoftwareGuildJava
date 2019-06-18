/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.SuperpowersDao;
import com.sg.superherosighting.dtos.Superpowers;
import com.sg.superherosighting.dtos.Supervillains;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ctrop
 */
@Service
public class SueprPowersServiceDBImpl implements SuperPowersService {

        @Autowired
    SuperpowersDao dao;

    public SueprPowersServiceDBImpl(SuperpowersDao dao) {
        this.dao = dao;
    }
    
    
    
    @Override
    public void createPower(Superpowers superpower) {
       dao.createPower(superpower);
    }

    @Override
    public Superpowers getPower(int superPowerId) {
     return dao.getPower(superPowerId);
    }

    @Override
    public List<Superpowers> getAllPowers() {
      return dao.getAllPowers();
    }

    @Override
    public void updatePower(Superpowers superpower) {
     dao.updatePower(superpower);
    }

    @Override
    public void deletePower(int superPowerId) {
      
       
       dao.deletePower(superPowerId);
    }
    
}
