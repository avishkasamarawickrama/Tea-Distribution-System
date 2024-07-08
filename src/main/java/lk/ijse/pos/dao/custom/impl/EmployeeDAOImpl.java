package lk.ijse.pos.dao.custom.impl;


import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.EmployeeDAO;
import lk.ijse.pos.entity.employee;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public ArrayList<employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<employee> allEmployee = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        while (rst.next()) {
            employee employee = new employee(rst.getString("employee_id"), rst.getString("name"), rst.getInt("contact"),rst.getString("status"),rst.getString("field_id"));
            allEmployee.add(employee);
        }
        return allEmployee;
    }

    @Override
    public boolean add(employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee (employee_id,name,contact) VALUES (?,?,?,?)", entity.getEmployee_id(), entity.getName(), entity.getContact(),entity.getStatus());
    }

    @Override
    public boolean update(employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET name=?,  contact=? ,status=? WHERE employee_id=?", entity.getName(), entity.getContact(),entity.getStatus(), entity.getEmployee_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM employee WHERE employee_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM employee ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("employee_id");
            int newEmployeeId = Integer.parseInt(id.replace("E00-", "")) + 1;
            return String.format("E00-%03d", newEmployeeId);
        } else {
            return "E00-001";
        }
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE employee_id=?", id);
    }


    @Override
    public employee search(String employee_id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE employee_id=?", employee_id + "");
        rst.next();
        return new employee(employee_id + "", rst.getString("name"), rst.getInt("contact"),rst.getString("status"),rst.getString("field_id"));
    }

}
