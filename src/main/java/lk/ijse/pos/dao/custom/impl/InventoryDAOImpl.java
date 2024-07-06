package lk.ijse.pos.dao.custom.impl;
import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.InventoryDAO;
import lk.ijse.pos.entity.inventory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOImpl implements InventoryDAO {

    @Override
    public ArrayList<inventory> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<inventory> allInventory = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM inventory");
        while (rst.next()) {
            inventory inventory = new inventory(rst.getString("category_id"), rst.getString("category_name"), rst.getInt("qtyOnHand"),rst.getBigDecimal("unitPrice"));
            allInventory.add(inventory);
        }
        return allInventory;
    }

    @Override
    public boolean add(inventory entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO inventory (category_no,category_name,qtyOnHand,unitPrice) VALUES (?,?,?,?)", entity.getCategory_id(), entity.getCategory_name(), entity.getQty(),entity.getUnitPrice());
    }

    @Override
    public boolean update(inventory entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE inventory SET category_name=?,  qtyOnHand=? ,unitPrice=? WHERE category_id=?", entity.getCategory_name(), entity.getQty(),entity.getUnitPrice());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT category_id FROM inventory WHERE category_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT category_id FROM inventory ORDER BY category_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("category_id");
            int newInventoryId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newInventoryId);
        } else {
            return "I00-001";
        }
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM inventory WHERE category_id=?", id);
    }


    @Override
    public inventory search(String category_id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM inventory WHERE category_id=?", category_id + "");
        rst.next();
        return new inventory(category_id + "", rst.getString("category_name"), rst.getInt("qtyOnHand"),rst.getBigDecimal("unitPrice"));
    }

}
