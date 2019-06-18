/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.OrganizationsDao;
import com.sg.superherosighting.dtos.Organizations;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ctrop
 */
@Service
public class OrganizationsServiceDBImpl implements OrganizationsService{

        @Autowired
    OrganizationsDao dao;

    public OrganizationsServiceDBImpl(OrganizationsDao dao) {
        this.dao = dao;
    }
    
    
    
    @Override
    public void createOrgan(Organizations organization) {
      dao.createOrgan(organization);
    }

    @Override
    public Organizations getOrganById(int organizationId) {
       return dao.getOrganById(organizationId);
    }

    @Override
    public List<Organizations> getAllOrgans() {
        return dao.getAllOrgans();
    }

    @Override
    public List<Organizations> listAllOrgansOfVillain(int villainID) {
     return dao.listAllOrgansOfVillain(villainID);
    }

    @Override
    public void updateOrgan(Organizations organization) {
     dao.updateOrgan(organization);
    }

    @Override
    public void deleteOrang(int organizationId) {
       dao.deleteOrang(organizationId);
    }
    
}
