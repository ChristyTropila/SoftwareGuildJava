/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

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

/**
 *
 * @author ctrop
 */
@Repository
public class SupervillainsDaoJDBCDB implements SupervillainsDao {

    @Autowired
    JdbcTemplate jdbc;

    //sql statements
    private final String Create = "INSERT INTO superVillains (villainName, descrip, superPowerId)" + " values (?,?,?)";
    private final String ReadAll = "Select * From superVillains";
    private final String ReadById = "Select * From superVillains where villainID = ?";
    private final String Update = "Update superVillains set villainName = ?, descrip = ?, superPowerId= ?  where villainID = ?";
    private final String Delete = "Delete From superVillains where villainID = ?";
    private final String DELETE_SUPERVILLAINS_SIGHTINGS = "Delete From superVillains_sightings WHERE villainID = ?";
    private final String SELECT_VILLAIN_BY_SIGHTING = "Select v.villainID, v.villainName, v.descrip,v.superPowerId FROM superVillains AS v INNER JOIN superVillains_sightings"
            + "AS vs on v.villainID = vs.villainID WHERE sightingId=?";
    //private final String INSERT_VILLAIN_ORGS = "Insert Into superVillains_organizations (villainID, organizationId) values(?,?)";
    private final String DELETE_VILLAIN_ORGS = "Delete From superVillains_organizations where villainID = ?";
    private final String SELECT_VILLAIN_ORGS_BY_ID = "Select v.villainID, v.villainName, v.descrip, v.superPowerId From superVillains AS v INNER Join superVillains_organizations AS ov ON "
            + "v.villainID=ov.villainID WHERE ov.organizationId =?";

   // private final String SELECT_ORGS_BY_VILLAIN = "SELECT o.organizationId, o.name, o.description, o.phoneNum, o.locationId FROM organizations AS o INNER JOIN superVillains_organizations AS so "
     //       + "ON o.organizationId = so.organizationId WHERE so.villainID = ?";

    //select superpower by villain
    private final String SELECT_POWER_BY_VILLAIN = "Select p.superPowerId, p.powerName, p.descrip From superpowers p Join superVillains on p.superPowerId = superVillains.superPowerId Where superVillains.villainID=?";
    private final String SELECT_VILLAN_BY_POWER= "Select * from superVillains  WHERE superPowerId =?";
   
    @Override
    public void createVillain(Supervillains villain) {
        jdbc.update(Create, villain.getVillainName(), villain.getDescription(), villain.getSuperPower().getSuperPowerId());
        int villainID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        villain.setVillainID(villainID);
    }

    @Override
    public Supervillains getVillainById(int villainID) {
        try {
            Supervillains sv = jdbc.queryForObject(ReadById, new SupervillainsMapper(), villainID);
            sv.setSuperPower(findSuperPowerForVilain(sv));
            return sv;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Supervillains> getAllVillains() {
        List<Supervillains> villains = jdbc.query(ReadAll, new SupervillainsMapper());
        return associateSuperpowerWithVillain(villains);
    }

    @Override
    public void updateVillain(Supervillains villain
    ) {
        jdbc.update(Update, villain.getVillainName(), villain.getDescription(), villain.getSuperPower().getSuperPowerId(), villain.getVillainID());

    }

    @Override
    public void deleteVillain(int villainID
    ) {
        jdbc.update(DELETE_SUPERVILLAINS_SIGHTINGS, villainID);
        jdbc.update(DELETE_VILLAIN_ORGS, villainID);
        jdbc.update(Delete, villainID);
    }

    @Override
    public List<Supervillains> getAllVillainsByOrgan(int organizationId){
        List<Supervillains>vList=jdbc.query(SELECT_VILLAIN_ORGS_BY_ID, new SupervillainsMapper(), organizationId);
        return associateSuperpowerWithVillain(vList);

    }

    @Override
    public List<Supervillains> getAllVillainsBySighting(int sightingId) {
      List<Supervillains>vList=jdbc.query(SELECT_VILLAIN_BY_SIGHTING, new SupervillainsMapper(), sightingId);
      return associateSuperpowerWithVillain(vList);
    }
    
    
    //helpers
    
    
    
    private List<Supervillains> associateSuperpowerWithVillain (List<Supervillains> vList){
        for(Supervillains c: vList){
            c.setSuperPower(findSuperPowerForVilain(c));
        }
        return vList;
    }
    
    private Superpowers findSuperPowerForVilain(Supervillains villain){
        return jdbc.queryForObject(SELECT_POWER_BY_VILLAIN, new SuperpowersMapper(), villain.getVillainID());
    }
    //mapper

    @Override
    public List<Supervillains> getAllVillainsByPower(int powerId) {
     List<Supervillains>vList= jdbc.query(SELECT_VILLAN_BY_POWER, new SupervillainsMapper(), powerId);
     return associateSuperpowerWithVillain (vList);
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
}
