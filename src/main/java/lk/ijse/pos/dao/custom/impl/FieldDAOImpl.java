package lk.ijse.pos.dao.custom.impl;


import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.FieldDAO;
import lk.ijse.pos.entity.fields;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FieldDAOImpl implements FieldDAO {

    @Override
    public ArrayList<fields> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<fields> allField = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM field");
        while (rst.next()) {
            fields field = new fields(rst.getString("field_id"), rst.getString("name"), rst.getString("address"));
            allField.add(field);
        }
        return allField;
    }

    @Override
    public boolean add(fields entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO field (field_id,name,address) VALUES (?,?,?)", entity.getField_id(), entity.getName(), entity.getAddress());
    }

    @Override
    public boolean update(fields entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE field SET name=?,  address=? WHERE field_id=?", entity.getName(), entity.getAddress());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT field_id FROM field WHERE field_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT field_id FROM field ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("field_id");
            int newFieldId = Integer.parseInt(id.replace("F00-", "")) + 1;
            return String.format("F00-%03d", newFieldId);
        } else {
            return "F00-001";
        }
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM field WHERE field_id=?", id);
    }


    @Override
    public fields search(String field_id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM field WHERE field_id=?", field_id + "");
        rst.next();
        return new fields(field_id + "", rst.getString("name"), rst.getString("address"));
    }

}
