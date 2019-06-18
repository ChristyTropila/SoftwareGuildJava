/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controllers;

import com.sg.superherosighting.dtos.Locations;
import com.sg.superherosighting.service.LocationsService;
import com.sg.superherosighting.service.OrganizationsService;
import com.sg.superherosighting.service.SightingsService;
import com.sg.superherosighting.service.SuperPowersService;
import com.sg.superherosighting.service.SuperVillainsService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author ctrop
 */
@Controller
public class LocationsController {

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
    
    

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Locations> locations = loc.getAllLocations();
        model.addAttribute("location", locations);
        return "locations";

    }

    @PostMapping("createLocation")
    public String createLocation(String name, String description, String streetName, String city, String state, String zipCode, Double longitude, Double latitude) {
        Locations locations = new Locations();
        locations.setName(name);
        locations.setDescription(description);
        locations.setStreetName(streetName);
        locations.setCity(city);
        locations.setState(state);
        locations.setZipCode(zipCode);
        locations.setLongitude(longitude);
        locations.setLatitude(latitude);

        
        
        loc.createLocation(locations);
        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer locationId) {

        loc.deleteLocation(locationId);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer locationId, Model model) {

        Locations location = loc.getLocationById(locationId);
        model.addAttribute("location", location);
        return "editLocation";

    }

    @PostMapping(value="editLocation")
   public String performEditLocation( HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("locationId"));

        Locations location = loc.getLocationById(id);
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setStreetName(request.getParameter("streetName"));
        location.setCity(request.getParameter("city"));
        location.setState(request.getParameter("state"));
        location.setZipCode(request.getParameter("zipCode"));
        location.setLongitude(new Double(request.getParameter("longitude")));
        location.setLatitude(new Double(request.getParameter("latitude")));
        
    
        
        loc.updateLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("detailsLocation")
    public String detailsLocation(Integer locationId, Model model) {
        Locations location = loc.getLocationById(locationId);
        model.addAttribute("location", location);
        return "detailsLocation";
    }

}
