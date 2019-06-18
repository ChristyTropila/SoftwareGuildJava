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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ctrop
 */
@Repository
public class SuperpowersDaoJDBCDB implements SuperpowersDao {

    @Autowired
    JdbcTemplate jdbc;

    //sql statements
    private final String Create = "INSERT INTO superpowers (powerName, descrip)" + " values (?,?)";
    private final String ReadAll = "Select * From superpowers";
    private final String ReadById = "Select * From superpowers where superPowerId = ?";
    private final String Update = "Update superpowers set powerName = ?, descrip = ?" + " where superPowerId = ?";
    private final String Delete = "Delete From superpowers where superPowerId = ?";
    private final String DeleteSupervillain = "Delete From superVillains where superPowerId = ?";
     private final String Deletevillainfromorg="DELETE supervillains_organizations,organizations FROM supervillains_organizations " +
"        INNER JOIN " +
"    organizations ON organizations.organizationId = supervillains_organizations.organizationId " +
"WHERE    organizations.villainID = ?";
         private final String BridgeTableSightingHero="DELETE supervillains_sightings,sightings FROM supervillains_sightings " +
"        INNER JOIN " +
"    sightings ON sightings.sightingId = supervillains_sightings.sightingid " +
"WHERE    sightings.villainID = ?";

    @Override
    @Transactional
    public void createPower(Superpowers superpower) {
        jdbc.update(Create, superpower.getPowerName(), superpower.getDescrip());
        int powerId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setSuperPowerId(powerId);
    }

    @Override
    public Superpowers getPower(int superPowerId) {
        //surrond with try catch in case power doesn't exist
        try {
            return jdbc.queryForObject(ReadById, new SuperpowersMapper(), superPowerId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Superpowers> getAllPowers() {
        return jdbc.query(ReadAll, new SuperpowersMapper());
    }

    @Override
    public void updatePower(Superpowers superpower) {
        
        jdbc.update(Update, superpower.getPowerName(), superpower.getDescrip(), superpower.getSuperPowerId());
        jdbc.update(DeleteSupervillain, superpower.getSuperPowerId());
        
    }

    @Override
    @Transactional
    public void deletePower(int superPowerId) {
   
     jdbc.update(DeleteSupervillain, superPowerId);
        jdbc.update(Delete, superPowerId);
    }
 

//superpower mapper
    private static final class SuperpowersMapper implements RowMapper<Superpowers> {

        @Override
        public Superpowers mapRow(ResultSet rs, int index) throws SQLException {
            Superpowers sp = new Superpowers();
            sp.setSuperPowerId(rs.getInt("superPowerId"));
            sp.setPowerName(rs.getString("powerName"));
            sp.setDescrip(rs.getString("descrip"));

            return sp;
        }
    }
}
