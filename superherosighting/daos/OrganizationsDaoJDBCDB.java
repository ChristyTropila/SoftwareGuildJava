/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.dtos.Locations;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Superpowers;
import com.sg.superherosighting.dtos.Supervillains;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class OrganizationsDaoJDBCDB implements OrganizationsDao {

    @Autowired
    JdbcTemplate jdbc;

    //sql statements
    private final String Create = "INSERT INTO organizations (name, description, phoneNum, locationId) values (?,?,?,?)";
    private final String ReadAll = "Select * From organizations";
    private final String ReadById = "Select * From organizations where organizationId = ?";
    private final String Update = "Update organizations set name = ?, description = ?, phoneNum= ?, locationId=? where organizationId = ?";
    private final String Delete = "Delete From organizations where organizationId = ?";

    private final String ReadBySuperVillainId = "SELECT o.organizationId, o.name, o.description, o.phoneNum, o.locationId FROM organizations AS o INNER JOIN superVillains_organizations AS so "
            + "ON o.organizationId = so.organizationId WHERE so.villainID = ?";

    
    private final String InsertIntoVillainOrgs = "Insert Into superVillains_organizations(villainID, organizationId) values (?,?)";
    private final String DeleteFromBridge = "Delete From superVillains_organizations WHERE organizationId = ?";
 @Override
    @Transactional
    public void createOrgan(Organizations organization) {
        jdbc.update(Create, organization.getName(), organization.getDescription(), organization.getPhoneNum(), organization.getLocation().getLocationId());
        int orgId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrganizationId(orgId);
        insertVillainOrg(organization);
    }

    @Override
    public Organizations getOrganById(int organizationId) {
        try {
            Organizations orgs = jdbc.queryForObject(ReadById, new OrganizationMapper(), organizationId);
            orgs.setLocation(findLocationForOrgan(orgs));
            orgs.setMembers(findVillainsForOrgans(orgs));
            return orgs;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organizations> getAllOrgans() {
        List<Organizations> o = jdbc.query(ReadAll, new OrganizationMapper());
        return associateLocationAndVillainsWithOrgs(o);
    }

    @Override
    public List<Organizations> listAllOrgansOfVillain(int villainID) {
        List<Organizations> o = jdbc.query(ReadBySuperVillainId, new OrganizationMapper(), villainID);
        return associateLocationAndVillainsWithOrgs(o);
    }

    @Override
    @Transactional
    public void updateOrgan(Organizations organization) {
        jdbc.update(Update, organization.getName(), organization.getDescription(), organization.getPhoneNum(), organization.getLocation().getLocationId(), organization.getOrganizationId());
        jdbc.update(DeleteFromBridge, organization.getOrganizationId());
        insertVillainOrg(organization);

    }

    @Override
    @Transactional
    public void deleteOrang(int organizationId) {
        jdbc.update(DeleteFromBridge, organizationId);
        jdbc.update(Delete, organizationId);
    }

    //helper methods
    private final String SelectLocByOrgId = "Select l.locationId, l.name, l.description, l.streetName, l.city, l.state, l.zipCode, l.longitude, l.latitude FROM locations "
            + "AS l INNER JOIN organizations AS o ON l.locationId=o.locationId WHERE o.organizationId = ?";

    private Locations findLocationForOrgan(Organizations orgs) {
        return jdbc.queryForObject(SelectLocByOrgId, new LocationMapper(), orgs.getOrganizationId());
    }

        private final String SELECT_POWER_BY_VILLAIN = "Select p.superPowerId, p.powerName, p.descrip From superpowers p Join superVillains on p.superPowerId = superVillains.superPowerId Where superVillains.villainID=?";  
  
         
    private Superpowers findSuperPowerForVilain(Supervillains villain){
        return jdbc.queryForObject(SELECT_POWER_BY_VILLAIN, new SuperpowersMapper(), villain.getVillainID());
    }
    
   
    private final String SelectVillainByOrgId="Select v.villainID, v.villainName, v.descrip, v.superPowerId FROM superVillains AS v INNER JOIN superVillains_organizations AS vo ON v.villainID = vo.villainID WHERE vo.organizationId= ?";
    private List<Supervillains> findVillainsForOrgans(Organizations orgs) {
      List<Supervillains>vList=jdbc.query(SelectVillainByOrgId, new SupervillainsMapper(), orgs.getOrganizationId());
      
      for(Supervillains c: vList){
         c.setSuperPower(findSuperPowerForVilain(c));
      }
      return vList;
    }

    private void insertVillainOrg(Organizations organization) {
        int orgId = organization.getOrganizationId();
        List<Supervillains> sv = organization.getMembers();

        for (Supervillains c : sv) {
            jdbc.update(InsertIntoVillainOrgs, c.getVillainID(), orgId);
        }
    }

    private List<Organizations> associateLocationAndVillainsWithOrgs(List<Organizations> o) {
     for(Organizations org: o){
         org.setLocation(findLocationForOrgan(org));
         org.setMembers(findVillainsForOrgans(org));
     }
     return o;
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