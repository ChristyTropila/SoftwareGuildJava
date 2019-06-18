/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.dtos.Organizations;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface OrganizationsDao {
    
    //crud methods
    
    public void createOrgan(Organizations organization);
    public Organizations getOrganById(int organizationId);
    public List<Organizations> getAllOrgans();
    public List<Organizations> listAllOrgansOfVillain(int villainID);
    public void updateOrgan(Organizations organization);
    public void deleteOrang(int organizationId);
    
    
    
    
}
