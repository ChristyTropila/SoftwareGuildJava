/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.dtos.Locations;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Sightings;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ctrop
 */

@Repository
public class LocationsDaoJDBCDB implements LocationsDao{
    
    @Autowired
    JdbcTemplate jdbc;
    
    
    //sql statements
    private final String Create="INSERT INTO locations (name, description, streetName, city, state, zipCode, longitude, latitude)" + " values (?,?,?,?,?,?,?,?)";
    private final String ReadAll="Select * From locations";
    private final String ReadById= "Select * From locations where locationId = ?";
    private final String ReadBySighting="Select * From sightings where locationId =?";
    private final String ReadLocInOrgs="Select * From organizations where locationId=?";
    private final String Update="Update locations SET name = ?, description = ?, streetName= ?, city= ?, state = ?, zipCode=?, longitude=?, latitude=?  where locationId = ?";
    private final String Delete="Delete From locations where locationId =?";
    private final String BridgeTable="Delete from sightings where locationId =?";
     private final String BridgeTableSightingHero="DELETE supervillains_sightings,sightings FROM supervillains_sightings " +
"        INNER JOIN " +
"    sightings ON sightings.sightingId = supervillains_sightings.sightingid " +
"WHERE    sightings.locationId = ?";
    private final String DeleteOrgFromSight="Delete from organizations where locationId=?";
     private final String Deletevillainfromorg="DELETE supervillains_organizations,organizations FROM supervillains_organizations " +
"        INNER JOIN " +
"    organizations ON organizations.organizationId = supervillains_organizations.organizationId " +
"WHERE    organizations.locationId = ?";
    @Override
    @Transactional
    public void createLocation(Locations location) {
     jdbc.update(Create, location.getName(),location.getDescription(), location.getStreetName(), location.getCity(), location.getState(), location.getZipCode(), location.getLongitude(), location.getLatitude());
     int newId=jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
     location.setLocationId(newId);
    }

    @Override
    public Locations getLocationById(int locationId) {
        try{
           return jdbc.queryForObject(ReadById, new LocationMapper(),locationId);
        }catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Locations> getAllLocations() {
     return jdbc.query(ReadAll, new LocationMapper());
    }

    @Override
    public void updateLocation(Locations location) {
      jdbc.update(Update, location.getName(), location.getDescription(), location.getStreetName(), location.getCity(), location.getState(), location.getZipCode(), location.getLongitude(), location.getLatitude(), location.getLocationId());
     
    }

    @Override
    public void deleteLocation(int locationId) {
        
     
       jdbc.update(BridgeTableSightingHero, locationId);
       jdbc.update(Deletevillainfromorg, locationId);
        
       jdbc.update(DeleteOrgFromSight, locationId);
       
        jdbc.update(Delete,locationId);
    }

      //organization mapper
    private static final class OrganizationMapper implements RowMapper<Organizations> {

        @Override
        public Organizations mapRow(ResultSet rs, int index) throws SQLException {
            Organizations orgs = new Organizations();
            orgs.setOrganizationId(rs.getInt("organizationId"));
            orgs.setName(rs.getString("name"));
            orgs.setDescription(rs.getString("description"));
            orgs.setPhoneNum(rs.getString("phoneNum"));

            return orgs;

        }
    }
    
    
        private static final class LocationMapper implements RowMapper<Locations>{
        @Override
        public Locations mapRow(ResultSet rs, int index) throws SQLException{
            Locations l= new Locations();
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

}
