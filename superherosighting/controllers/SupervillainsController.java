/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controllers;

import com.sg.superherosighting.daos.OrganizationsDao;
import com.sg.superherosighting.daos.SightingsDao;
import com.sg.superherosighting.daos.SuperpowersDao;
import com.sg.superherosighting.daos.SupervillainsDao;
import com.sg.superherosighting.dtos.Locations;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Superpowers;
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
//@RequestMapping({"/supervillains"})
public class SupervillainsController {


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
    
    
    @GetMapping("supervillains")
    public String displaySuperVillains(Model model) {
        List<Supervillains> villain = ss.getAllVillains();
        List<Superpowers> powers = sp.getAllPowers();
        model.addAttribute("supervillains", villain);
        model.addAttribute("superpowers", powers);
        return "supervillains";
    }

    @PostMapping("createVillain")
    public String createVillain(HttpServletRequest request, Supervillains villains) {
        String superPowerId = request.getParameter("superPowerId");
        villains.setSuperPower(sp.getPower(Integer.parseInt(superPowerId)));
        ss.createVillain(villains);
        return "redirect:/supervillains";

    }

    @GetMapping("deleteVillain")
    public String deleteVillain(Integer villainID) {

        ss.deleteVillain(villainID);

        return "redirect:/supervillains";
    }
    
    
    @GetMapping("detailsVillain")
    public String detailsVillain(Integer villainID, Model model){
        Supervillains villain=ss.getVillainById(villainID);
        model.addAttribute("villain", villain);
        return "detailsVillain";
    }
    

    
    
    @GetMapping("editVillain")
    public String editVillain(Integer villainID, Model model){
        Supervillains villain= ss.getVillainById(villainID);
        List<Superpowers>powers=sp.getAllPowers();
        model.addAttribute("villain", villain);
        model.addAttribute("powers", powers);
        return "editVillain";
    }

    @PostMapping("editVillain")
    public String perfromEditVillain(Supervillains villain,HttpServletRequest request){
      String superPowerId=request.getParameter("superPowerId");
 
      villain.setSuperPower(sp.getPower(Integer.parseInt(superPowerId)));
      ss.updateVillain(villain);
       
        return "redirect:/supervillains";
    }
 

    
}
