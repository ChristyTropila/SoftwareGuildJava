/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controllers;

import com.sg.superherosighting.daos.LocationsDao;
import com.sg.superherosighting.daos.OrganizationsDao;
import com.sg.superherosighting.daos.SightingsDao;
import com.sg.superherosighting.daos.SuperpowersDao;
import com.sg.superherosighting.daos.SupervillainsDao;
import com.sg.superherosighting.dtos.Locations;
import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Supervillains;
import com.sg.superherosighting.service.LocationsService;
import com.sg.superherosighting.service.OrganizationsService;
import com.sg.superherosighting.service.SightingsService;
import com.sg.superherosighting.service.SuperPowersService;
import com.sg.superherosighting.service.SuperVillainsService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ctrop
 */

@Controller
public class SightingsController {
 
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
 

    

 @GetMapping("sightings")
 public String displaySightings(Model model){
     List<Sightings>sights=sd.viewAllSighting();
     List<Locations>locations=loc.getAllLocations();
     List<Supervillains>sv=ss.getAllVillains();
     
     model.addAttribute("sightings",sights);
     model.addAttribute("locations", locations);
     model.addAttribute("supervillains", sv);
     return "sightings";
 }

 
 @PostMapping("createSighting")
 public String createSighting(HttpServletRequest request, int locationId, int[]villainID){
     Sightings sight=new Sightings();
     sight.setDate(LocalDate.parse(request.getParameter("date"),DateTimeFormatter.ISO_DATE));
     Locations location=new Locations();
     location.setLocationId(locationId);
     sight.setLocation(location);
     
     List<Supervillains>vList=new ArrayList<>();
     List<Supervillains>villainList=ss.getAllVillains();
     for(int id:villainID){
         for(Supervillains villain:villainList){
             if(villain.getVillainID()==id)
                 vList.add(villain);}}
     sight.setSuperVillain(vList);
     sd.addSighting(sight);
     return"redirect:/sightings";
         }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model){
        String sightIdParam=request.getParameter("sightingId");
        int sightId=Integer.parseInt(sightIdParam);
        Sightings sightings = sd.viewSighting(sightId);
        model.addAttribute("sight", sightings);
        List<Supervillains>vList=ss.getAllVillains();
        List<Locations>lList=loc.getAllLocations();
        model.addAttribute("locations", lList);
        model.addAttribute("members", vList);
        return "editSighting";
    }
   
    
    
      @PostMapping("editSighting")
    public String performEditSighting(Sightings sightings, HttpServletRequest request){
    
      sightings.setDate(LocalDate.parse(request.getParameter("date"),DateTimeFormatter.ISO_DATE));  
        
        String locationId = request.getParameter("locationId");
        String[] villainIDs = request.getParameterValues("villainID");

        sightings.setLocation(loc.getLocationById(Integer.parseInt(locationId)));

        List<Supervillains> villains = new ArrayList<>();
        for (String villainID : villainIDs) {
            villains.add(ss.getVillainById(Integer.parseInt(villainID)));
        }
        sightings.setSuperVillain(villains);
        sd.updateSighting(sightings);
        return "redirect:/sightings";
        
    
    }

    
    @GetMapping("detailsSighting")
    public String detailsSighting(Integer sightingId, Model model){
        Sightings sight=sd.viewSighting(sightingId);
        model.addAttribute("sight", sight);
        return "detailsSighting";
    }
    
    
    
        @GetMapping("deleteSighting")
    public String deleteSighting(Integer sightingId) {

      sd.deleteSighting(sightingId);

        return "redirect:/sightings";
        
    }
    
   
    @GetMapping("/")
    public String sightingList(Model model){
        List<Sightings>sList= sd.showLastTenSightings();
        List<Supervillains>vList=ss.getAllVillains();
        model.addAttribute("sight", sList);
        model.addAttribute("villain", vList);
                
     
        
        return"index";
        
    }
    
}





    

