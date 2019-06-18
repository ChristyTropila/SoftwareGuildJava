/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.dtos.Locations;
import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Superpowers;
import com.sg.superherosighting.dtos.Supervillains;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ctrop
 */

@Repository
public class SightingsDaoJDBCDB implements SightingsDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    
    //sql statements
    
    private final String Create = "INSERT INTO sightings (sightingDate, locationId)" + " values (?,?)";
    private final String ReadAll = "Select * From sightings";
    private final String ReadById = "Select * From sightings where sightingId = ?";
    private final String Update = "Update sightings set sightingDate = ?, locationId = ? where sightingId = ?";
    private final String Delete = "Delete From sightings where sightingId = ?";
    private final String DeleteVillainsSighting = "Delete From superVillains_sightings Where sightingId = ?";
    private final String ReadLoc="Select * From sightings WHERE locationId = ?";
    private final String ReadByDate="Select superVillains.*, locations.*, sightings.sightingId, sightings.sightingDate FROM locations INNER JOIN sightings on locations.locationId = sightings.locationId INNER JOIN superVillains_sightings ON superVillains_sightings.sightingId=sightings.sightingId INNER JOIN superVillains ON superVillains.villainID=superVillains_sightings.villainID WHERE sightings.sightingDate=?";
    private final String DeleteSightingVillain= "Delete From superVillains_sightings where villainID = ?";
    private final String CreateVillainSighting = "Insert into superVillains_sightings(villainID,sightingId)" + "values(?,?)";

    private final String ReadVillainBySighting = "Select v.villainID, v.villainName, v.descrip, v.superPowerId From superVillains v Join superVillains_sightings vs On sightingId Where v.villainID = vs.villainID And vs.sightingId = ?";
    private final String ReadLocationBySighting = "Select l.locationId, l.name, l.description, l.streetName, l.city, l.state, l.zipCode, l.longitude, l.latitude From locations l Join sightings on l.locationId = sightings.locationId Where sightings.sightingId= ?";

    private final String ReadSightingByVillain="Select s.sightingId, s.sightingDate FROM sightings AS s INNER JOIN supervillains_sightings AS vs ON s.sightingId = vs.sightingId WHERE villainID = ? ";
    private final String ShowLastTenSightings = "Select * From sightings Order By sightings.sightingDate DESC limit 0,10";
      private final String SELECT_POWER_BY_VILLAIN = "Select p.superPowerId, p.powerName, p.descrip From superpowers p Join superVillains on p.superPowerId = superVillains.superPowerId Where superVillains.villainID=?";

    

    @Override
    @Transactional
    public void addSighting(Sightings sighting) {
      jdbc.update(Create, sighting.getDate().toString(), sighting.getLocation().getLocationId());
      int sightId=jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
      sighting.setSightingId(sightId);
      updateVillainSighting(sighting);
    }

    @Override
    public Sightings viewSighting(int sightingId) {
         try{
             Sightings s= jdbc.queryForObject(ReadById, new SightingMapper(), sightingId);
             s.setLocation(findLocForSight(s));
             s.setSuperVillain(findVillainsForSight(s));
             return s;
         }catch (EmptyResultDataAccessException e){
             return null;
         }
    }

    @Override
    public List<Sightings> viewAllSighting() {
     List<Sightings> sList= jdbc.query(ReadAll, new SightingMapper());
     return associateLocAndVillainWithSightings(sList);

    }

    @Override
    public List<Sightings> viewAllSightingsByDate(LocalDate date) {
       List<Sightings>sL= jdbc.query(ReadByDate, new SightingMapper(),date.toString());
       sL=associateLocAndVillainWithSightings(sL);
       return sL;
    }

    @Override
    @Transactional
    public void updateSighting(Sightings sighting) {
        jdbc.update(Update, sighting.getDate().toString(), sighting.getLocation().getLocationId(), sighting.getSightingId());
        jdbc.update(DeleteVillainsSighting, sighting.getSightingId());
        updateVillainSighting(sighting);
    }

    @Override
    public void deleteSighting(int sightingId) {
      jdbc.update(DeleteVillainsSighting, sightingId);
      jdbc.update(Delete, sightingId);

    }

    @Override
    public List<Sightings> showLastTenSightings() {
    List<Sightings>sL=jdbc.query(ShowLastTenSightings, new SightingMapper());
    return associateLocAndVillainWithSightings(sL);
    }
    
    
    @Override
    public List<Sightings> getSightingsByVillain(int villainID) {
     List<Sightings>sL=jdbc.query(ReadSightingByVillain, new SightingMapper(), villainID);
     return associateLocAndVillainWithSightings(sL);
    }

    @Override
    public List<Sightings> getSightingByLoc(int locationId) {
      List<Sightings> sL= jdbc.query(ReadLoc, new SightingMapper(), locationId);
      return associateLocAndVillainWithSightings(sL);
    }
    
    
    
    //helper

    private void updateVillainSighting(Sightings sighting) {
     int sightId= sighting.getSightingId();
     List<Supervillains>villains= sighting.getSuperVillain();
     
     for(Supervillains c: villains){
         jdbc.update(CreateVillainSighting, c.getVillainID(),sightId);
     }
    }

    private List<Supervillains> findVillainsForSight(Sightings s) {
     List< Supervillains> sv= jdbc.query(ReadVillainBySighting, new SupervillainsMapper(), s.getSightingId());
   
     for(Supervillains villain:sv){
         villain.setSuperPower(findSuperPowerForVillain(villain));
     }
     return sv;
    }

    private Locations findLocForSight(Sightings s) {
      return jdbc.queryForObject(ReadLocationBySighting, new LocationMapper(), s.getSightingId());
    }

    private List<Sightings> associateLocAndVillainWithSightings(List<Sightings> sList) {
     for(Sightings c: sList){
         c.setSuperVillain(findVillainsForSight(c));
         c.setLocation(findLocForSight(c));
     }
     return sList;
    }

    private Superpowers findSuperPowerForVillain(Supervillains sv) {
        return jdbc.queryForObject(SELECT_POWER_BY_VILLAIN, new SuperpowersMapper(), sv.getVillainID());

    }

 


     //sighting mapper
    private static final class SightingMapper implements RowMapper<Sightings> {

        @Override
        public Sightings mapRow(ResultSet rs, int index) throws SQLException {
            Sightings sightings = new Sightings();
            sightings.setSightingId(rs.getInt("sightingId"));
            sightings.setDate(rs.getTimestamp("sightingDate").toLocalDateTime().toLocalDate());
            return sightings;
        }
    }

    //locations mapper
    private static final class LocationMapper implements RowMapper<Locations> {

        @Override
        public Locations mapRow(ResultSet rs, int index) throws SQLException {
            Locations l = new Locations();
            l.setLocationId(rs.getInt("locationId"));
            l.setName(rs.getString("name"));
            l.setDescription(rs.getString("description"));
            l.setStreetName(rs.getString("streetName"));
            l.setCity(rs.getString("city"));
            l.setState(rs.getString("state"));
            l.setZipCode(rs.getString("zipCode"));
            l.setLongitude(rs.getDouble("longitude"));
            l.setLatitude(rs.getDouble("latitude"));
            return l;
        }
    }

    //superVillains mapper
    public static final class SupervillainsMapper implements RowMapper<Supervillains> {

        @Override
        public Supervillains mapRow(ResultSet rs, int index) throws SQLException {
            Supervillains villain = new Supervillains();
            villain.setVillainID(rs.getInt("villainID"));
            villain.setVillainName(rs.getString("villainName"));
            villain.setDescription(rs.getString("descrip"));

            return villain;

        }
    }
    
    //superpower mapper
 private static final class SuperpowersMapper implements RowMapper<Superpowers>{
        @Override
        public Superpowers mapRow(ResultSet rs, int index) throws SQLException{
            Superpowers sp= new Superpowers();
            sp.setSuperPowerId(rs.getInt("superPowerId"));
            sp.setPowerName(rs.getString("powerName"));
            sp.setDescrip(rs.getString("descrip"));
            
            return sp;
        }
    }
    
    
}