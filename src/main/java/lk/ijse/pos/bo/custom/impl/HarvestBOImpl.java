package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.HarvestBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.FieldDAO;
import lk.ijse.pos.dao.custom.HarvestDAO;
import lk.ijse.pos.dto.HarvestDTO;
import lk.ijse.pos.dto.fieldsDTO;
import lk.ijse.pos.entity.Harvest;
import lk.ijse.pos.entity.fields;

import java.sql.SQLException;
import java.util.ArrayList;

public class HarvestBOImpl implements HarvestBO {


    HarvestDAO harvestDAO = (HarvestDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.HARVEST);
    @Override
    public ArrayList<HarvestDTO> getAllHarvest() throws SQLException, ClassNotFoundException {
        ArrayList<HarvestDTO> allHarvest= new ArrayList<>();
        ArrayList<Harvest> all = harvestDAO.getAll();
        for (Harvest h : all) {
            allHarvest.add(new HarvestDTO(h.getHarvest_no(),h.getQty(),h.getDate(),h.getField_id()));
        }
        return allHarvest;
    }

    @Override
    public boolean addHarvest(HarvestDTO dto) throws SQLException, ClassNotFoundException {
        return harvestDAO.add(new Harvest(dto.getHarvest_no(), dto.getQty(), dto.getDate(), dto.getField_id()));
    }

    @Override
    public boolean updateHarvest (HarvestDTO dto) throws SQLException, ClassNotFoundException {
        return harvestDAO.update(new Harvest(dto.getHarvest_no(), dto.getQty(), dto.getDate(), dto.getField_id()));
    }

    @Override
    public boolean existHarvest(String id) throws SQLException, ClassNotFoundException {
        return harvestDAO.exist(id);
    }

    @Override
    public boolean deleteHarvest(String id) throws SQLException, ClassNotFoundException {
        return harvestDAO.delete(id);
    }

    @Override
    public String generateNewHarvestID() throws SQLException, ClassNotFoundException {
        return harvestDAO.generateNewID();
    }


}

