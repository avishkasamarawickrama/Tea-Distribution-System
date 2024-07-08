package lk.ijse.pos.bo.custom.impl;
import lk.ijse.pos.bo.custom.InventoryBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.InventoryDAO;
import lk.ijse.pos.dto.inventoryDTO;
import lk.ijse.pos.entity.inventory;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBO {


    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INVENTORY);
    @Override
    public ArrayList<inventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException {
        ArrayList<inventoryDTO> allInventory= new ArrayList<>();
        ArrayList<inventory> all = inventoryDAO.getAll();
        for (inventory i : all) {
            allInventory.add(new inventoryDTO(i.getCategory_id(),i.getCategory_name(),i.getQty(),i.getUnitPrice(),i.getHarvest_no()));
        }
        return allInventory;
    }

    @Override
    public boolean addInventory(inventoryDTO dto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.add(new inventory(dto.getCategory_id(), dto.getCategory_name(), dto.getQtyOnHand(), dto.getUnitPrice(),dto.getHarvest_no()));
    }

    @Override
    public boolean updateInventory (inventoryDTO dto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.update(new inventory(dto.getCategory_id(), dto.getCategory_name(), dto.getQtyOnHand(), dto.getUnitPrice(),dto.getHarvest_no()));
    }

    @Override
    public boolean existInventory(String id) throws SQLException, ClassNotFoundException {
        return inventoryDAO.exist(id);
    }

    @Override
    public boolean deleteInventory(String id) throws SQLException, ClassNotFoundException {
        return inventoryDAO.delete(id);
    }

    @Override
    public String generateNewInventoryID() throws SQLException, ClassNotFoundException {
        return inventoryDAO.generateNewID();
    }

}
