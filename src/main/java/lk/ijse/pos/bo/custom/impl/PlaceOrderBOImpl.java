package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.PlaceOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.*;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.dto.OrdersDTO;
import lk.ijse.pos.dto.inventoryDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.OrderDetail;
import lk.ijse.pos.entity.Orders;
import lk.ijse.pos.entity.inventory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    FieldDAO fieldDAO = (FieldDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FIELD);
    HarvestDAO harvestDAO = (HarvestDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.HARVEST);
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INVENTORY);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(id);
        return new CustomerDTO(c.getCustomer_id(),c.getName(),c.getEmail(),c.getContact());
    }


    @Override
    public inventoryDTO searchInventory(String category_id) throws SQLException, ClassNotFoundException {
        inventory i = inventoryDAO.search(category_id);
        return new inventoryDTO(i.getCategory_id(),i.getCategory_name(),i.getQty(),i.getUnitPrice());
    }

    @Override
    public boolean existInventory(String category_id) throws SQLException, ClassNotFoundException {
        return inventoryDAO.exist(category_id);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return ordersDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerEntityData = customerDAO.getAll();
        ArrayList<CustomerDTO> convertToDto= new ArrayList<>();
        for (Customer c : customerEntityData) {
            convertToDto.add(new CustomerDTO(c.getCustomer_id(),c.getName(),c.getEmail(),c.getContact()));
        }
        return convertToDto;
    }

    @Override
    public ArrayList<inventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException {
        ArrayList<inventory> entityTypeData = inventoryDAO.getAll();
        ArrayList<inventoryDTO> dtoTypeData= new ArrayList<>();
        for (inventory i : entityTypeData) {
            dtoTypeData.add(new inventoryDTO(i.getCategory_id(),i.getCategory_name(),i.getQty(),i.getUnitPrice()));
        }
        return dtoTypeData;
    }


    @Override
    public boolean placeOrder(OrdersDTO dto)throws SQLException, ClassNotFoundException{
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            boolean b1 = ordersDAO.exist(dto.getOrder_id());
            /*if order id already exist*/
            if (b1) {
                return false;
            }

            connection.setAutoCommit(false);
            //Save the Order to the order table
            boolean b2 = ordersDAO.add(new Orders(dto.getOrder_id(), dto.getDate(), dto.getCustomer_id()));
            if (!b2) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO d : dto.getOrderDetail()) {
                OrderDetail orderDetails = new OrderDetail(d.getOrder_id(),d.getCategory_id(),d.getQty(),d.getUnitPrice());
                boolean b3 = orderDetailsDAO.add(orderDetails);
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                //Search & Update Item
                inventoryDTO inventory = findInventory(d.getCategory_id());
                inventory.setQtyOnHand(inventory.getQtyOnHand() - d.getQty());

                //update item
                boolean b = inventoryDAO.update(new inventory(inventory.getCategory_id(), inventory.getCategory_name(), inventory.getQtyOnHand(),inventory.getUnitPrice(),inventory.getHarvest_no()));

                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public inventoryDTO findInventory(String category_id)throws SQLException, ClassNotFoundException {
        try {
            inventory i = inventoryDAO.search(category_id);
            return new inventoryDTO(i.getCategory_id(),i.getCategory_name(),i.getQty(),i.getUnitPrice(),i.getHarvest_no());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Inventory " + category_id, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
