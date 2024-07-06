package lk.ijse.pos.dao.custom.impl;
import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.OrdersDAO;
import lk.ijse.pos.entity.Orders;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> allOrders = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders");
        while (rst.next()) {
            Orders orders = new Orders(rst.getString("order_id"), rst.getDate("date"));
            allOrders.add(orders);
        }
        return allOrders;
    }

    @Override
    public boolean add(Orders entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders (order_id,date) VALUES (?,?)", entity.getOrder_id(), entity.getDate());
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE orders SET date=? WHERE order_id=?", entity.getDate());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM orders WHERE order_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("order_id");
            int newOrdersId = Integer.parseInt(id.replace("OID-", "")) + 1;
            return String.format("OID-%03d", newOrdersId);
        } else {
            return "OID-001";
        }
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM orders WHERE order_id=?", id);
    }


    @Override
    public Orders search(String order_id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders WHERE order_id=?", order_id + "");
        rst.next();
        return new Orders(order_id + "", rst.getDate("date"));
    }

}
