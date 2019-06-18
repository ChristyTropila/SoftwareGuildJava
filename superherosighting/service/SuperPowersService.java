/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dtos.Superpowers;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface SuperPowersService {

      public void createPower(Superpowers superpower);

    public Superpowers getPower(int superPowerId);

    public List<Superpowers> getAllPowers();

    public void updatePower(Superpowers superpower);

    public void deletePower(int superPowerId);
}
