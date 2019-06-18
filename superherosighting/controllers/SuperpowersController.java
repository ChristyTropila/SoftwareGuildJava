/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controllers;

import com.sg.superherosighting.daos.OrganizationsDao;
import com.sg.superherosighting.daos.SuperpowersDao;
import com.sg.superherosighting.daos.SupervillainsDao;
import com.sg.superherosighting.dtos.Superpowers;
import com.sg.superherosighting.dtos.Supervillains;
import com.sg.superherosighting.service.LocationsService;
import com.sg.superherosighting.service.OrganizationsService;
import com.sg.superherosighting.service.SightingsService;
import com.sg.superherosighting.service.SuperPowersService;
import com.sg.superherosighting.service.SuperVillainsService;
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
public class SuperpowersController {
  
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
    
    @GetMapping("superpowers")
    public String displaySuperPowers(Model model){
        List<Superpowers>powers= sp.getAllPowers();
        model.addAttribute("powers", powers);
        return "superpowers";
         
    }
    
    @PostMapping("createPower")
    public String createPower(String powerName, String descrip){
       Superpowers powers= new Superpowers();
        powers.setPowerName(powerName);
        powers.setDescrip(descrip);
       
        sp.createPower(powers);
        return "redirect:/superpowers";
    }
  
    @GetMapping("deletePower")
    public String deletePower(Integer superPowerId){
       
        sp.deletePower(superPowerId);
        
        return "redirect:/superpowers";
    }
    
   @GetMapping("editPower")
    public String editPower(Integer superPowerId, Model model){

        Superpowers power=sp.getPower(superPowerId);
        model.addAttribute("power", power);
        return "editPower";
        
    }
    
    @PostMapping("editPower")
    public String performEditPower(HttpServletRequest request){
        int id= Integer.parseInt(request.getParameter("superPowerId"));
       
        Superpowers power= sp.getPower(id);
        power.setPowerName(request.getParameter("powerName"));
        power.setDescrip(request.getParameter("descrip"));
    
        sp.updatePower(power);
     return "redirect:/superpowers";
}

 @GetMapping("detailsPower")
    public String detailsPower(Integer superPowerId, Model model){
        Superpowers powers=sp.getPower(superPowerId);
        model.addAttribute("powers", powers);
        return "detailsPower";
    }
    
}
