package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.HarvestDTO;
import lk.ijse.pos.dto.inventoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryBO extends SuperBO {

    ArrayList<inventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException;

    boolean addInventory(inventoryDTO dto) throws SQLException, ClassNotFoundException ;

    boolean updateInventory(inventoryDTO dto) throws SQLException, ClassNotFoundException ;

    boolean existInventory(String id) throws SQLException, ClassNotFoundException;

    boolean deleteInventory(String id) throws SQLException, ClassNotFoundException;

    String generateNewInventoryID() throws SQLException, ClassNotFoundException;
}
