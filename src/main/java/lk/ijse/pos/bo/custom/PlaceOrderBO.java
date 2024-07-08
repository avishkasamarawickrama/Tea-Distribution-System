package lk.ijse.pos.bo.custom;

import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrdersDTO;
import lk.ijse.pos.dto.inventoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO {

     CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException ;

     inventoryDTO searchInventory(String category_id) throws SQLException, ClassNotFoundException ;

     boolean existInventory (String category_id) throws SQLException, ClassNotFoundException;

     boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;

     String generateOrderID() throws SQLException, ClassNotFoundException ;

     ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

     ArrayList<inventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException;

     boolean placeOrder(OrdersDTO dto)throws SQLException, ClassNotFoundException;

     inventoryDTO findInventory(String category_id)throws SQLException, ClassNotFoundException;

}
