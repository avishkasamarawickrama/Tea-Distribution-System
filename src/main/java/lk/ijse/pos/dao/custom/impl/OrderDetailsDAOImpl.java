package lk.ijse.pos.dao.custom.impl;
import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.OrderDetailsDAO;
import lk.ijse.pos.entity.OrderDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> allOrderDetail = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM orderDetails");
        while (rst.next()) {
            OrderDetail orderDetail = new OrderDetail(rst.getString("order_id"), rst.getString("category_id"), rst.getInt("qty"),rst.getBigDecimal("unitPrice"));
            allOrderDetail.add(orderDetail);
        }
        return allOrderDetail;
    }

    @Override
    public boolean add(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orderDetails (order_id,category_id,qty,unitPrice) VALUES (?,?,?,?)", entity.getOrder_id(), entity.getCategory_id(), entity.getQty(),entity.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE orderDetails SET category_id=?,  qty=? ,unitPrice=? WHERE order_id=?", entity.getCategory_id(), entity.getQty(),entity.getUnitPrice());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM orderDetails WHERE order_id =?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM orderDetails ORDER BY order_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("order_id");
            int newOrderDetailsId = Integer.parseInt(id.replace("OID-", "")) + 1;
            return String.format("OID-%03d", newOrderDetailsId);
        } else {
            return "OID-001";
        }
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM orderDetails WHERE order_id=?", id);
    }


    @Override
    public OrderDetail search(String order_id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM orderDetails WHERE order_id=?", order_id + "");
        rst.next();
        return new OrderDetail(order_id + "", rst.getString("category_id"), rst.getInt("qty"),rst.getBigDecimal("unitPrice"));
    }

}
