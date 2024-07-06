package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.entity.CustomEntity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomEntity> searchOrder(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLUtil.execute("SELECT orders.order_id,orders.date,orders.customer_id,orderDetails.order_id,orderDetails.category_id,orderDetails.qty,orderDetails.unitPrice from orders inner join orderDetails on orders.order_id=orderDetails.order_id where orders.order_id=?",oid);
        ArrayList<CustomEntity> allRecords= new ArrayList<>();
        while (rst.next()) {
            String order_id = rst.getString("order_id");
            String date = rst.getString("date");
            String customer_id = rst.getString("customer_id");
            String category_id = rst.getString("category_id");
            int qty = rst.getInt("qty");
            BigDecimal unitPrice = rst.getBigDecimal("unitPrice");

            CustomEntity customEntity = new CustomEntity(order_id, LocalDate.parse(date), customer_id, category_id, qty, unitPrice);
            allRecords.add(customEntity);
        }
        return allRecords;
    }
}
