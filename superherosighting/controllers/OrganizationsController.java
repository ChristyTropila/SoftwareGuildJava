/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controllers;

import com.sg.superherosighting.daos.LocationsDao;
import com.sg.superherosighting.daos.SightingsDao;
import com.sg.superherosighting.daos.SuperpowersDao;
import com.sg.superherosighting.dtos.Locations;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Supervillains;
import com.sg.superherosighting.service.LocationsService;
import com.sg.superherosighting.service.OrganizationsService;
import com.sg.superherosighting.service.SightingsService;
import com.sg.superherosighting.service.SuperPowersService;
import com.sg.superherosighting.service.SuperVillainsService;
import java.util.ArrayList;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ctrop
 */
@Controller
public class OrganizationsController {

    @Autowired
   SuperVillainsService ss;
   // SupervillainsDao ss;

    @Autowired
     OrganizationsService os;       
  //  OrganizationsDao os;

    @Autowired
   SuperPowersService sp;      
  //  SuperpowersDao sp;

    @Autowired
   SightingsService sd;
 //   SightingsDao sd;

    @Autowired
   LocationsService loc;
//    LocationsDao loc;

    @GetMapping("organizations")
    public String displayOrgs(Model model) {
        List<Organizations> organs = os.getAllOrgans();
        List<Locations> locations = loc.getAllLocations();
        List<Supervillains> supervillains = ss.getAllVillains();

        model.addAttribute("organizations", organs);
        model.addAttribute("locations", locations);
        model.addAttribute("supervillains", supervillains);
        return "organizations";

    }

    @PostMapping("createOrganization")
    public String createOrganization(Organizations organ, HttpServletRequest request) {
        String locationId = request.getParameter("locationId");
        String[] villainIDs = request.getParameterValues("villainID");
        organ.setLocation(loc.getLocationById(Integer.parseInt(locationId)));
        List<Supervillains> villains = new ArrayList<>();
        for (String villainID : villainIDs) {
            villains.add(ss.getVillainById(Integer.parseInt(villainID)));
        }
        organ.setMembers(villains);
        os.createOrgan(organ);
        return "redirect:/organizations";

    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer organizationId, Model model) {
        Organizations organs = os.getOrganById(organizationId);
        List<Locations> locations = loc.getAllLocations();
        List<Supervillains> members = ss.getAllVillains();

        model.addAttribute("organs", organs);
        model.addAttribute("locations", locations);
        model.addAttribute("members", members);
        return "editOrganization";

    }

 
    @PostMapping("editOrganization")
    public String performEditOrganization(Organizations organs, HttpServletRequest request) {
        String locationId = request.getParameter("locationId");
        String[] villainIDs = request.getParameterValues("villainID");

        organs.setLocation(loc.getLocationById(Integer.parseInt(locationId)));

        List<Supervillains> villains = new ArrayList<>();
        for (String villainID : villainIDs) {
            villains.add(ss.getVillainById(Integer.parseInt(villainID)));
        }
        organs.setMembers(villains);
        os.updateOrgan(organs);
        return "redirect:/organizations";

    }

    
        
    @GetMapping("detailsOrganization")
    public String detailsOrganization(Integer organizationId, Model model){
        Organizations organ= os.getOrganById(organizationId);
        model.addAttribute("organ", organ);
        return "detailsOrganization";
    }
    

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer organizationId) {

        os.deleteOrang(organizationId);

        return "redirect:/organizations";
    }

}
