package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.HarvestDTO;
import lk.ijse.pos.dto.fieldsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HarvestBO extends SuperBO {

    ArrayList<HarvestDTO> getAllHarvest() throws SQLException, ClassNotFoundException;

    boolean addHarvest(HarvestDTO dto) throws SQLException, ClassNotFoundException ;

    boolean updateHarvest(HarvestDTO dto) throws SQLException, ClassNotFoundException ;

    boolean existHarvest(String id) throws SQLException, ClassNotFoundException;

    boolean deleteHarvest(String id) throws SQLException, ClassNotFoundException;

    String generateNewHarvestID() throws SQLException, ClassNotFoundException;
}
