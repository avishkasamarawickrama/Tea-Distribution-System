package lk.ijse.pos.dao.custom.impl;
import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.SalaryDAO;
import lk.ijse.pos.entity.salary;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryDAOImpl implements SalaryDAO {

    @Override
    public ArrayList<salary> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<salary> allSalary = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM salary");
        while (rst.next()) {
            salary salary = new salary(rst.getString("no"), rst.getBigDecimal("amount"), rst.getDate("date"));
            allSalary.add(salary);
        }
        return allSalary;
    }

    @Override
    public boolean add(salary entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO salary (no,amount,date) VALUES (?,?,?)", entity.getNo(), entity.getAmount(), entity.getDate());
    }

    @Override
    public boolean update(salary entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE salary SET amount=?,  date=? WHERE no=?", entity.getAmount(), entity.getDate());
    }

    @Override
    public boolean exist(String no) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT no FROM salary WHERE no=?", no);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT no FROM salary ORDER BY no DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("no");
            int newSalaryId = Integer.parseInt(id.replace("S00-", "")) + 1;
            return String.format("S00-%03d", newSalaryId);
        } else {
            return "S00-001";
        }
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM salary WHERE no=?", id);
    }


    @Override
    public salary search(String no) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM salary WHERE no=?", no + "");
        rst.next();
        return new salary(no + "", rst.getBigDecimal("amount"), rst.getDate("date"));
    }

}
