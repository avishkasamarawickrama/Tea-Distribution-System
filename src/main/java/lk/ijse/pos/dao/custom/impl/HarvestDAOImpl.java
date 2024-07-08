package lk.ijse.pos.dao.custom.impl;
import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.HarvestDAO;
import lk.ijse.pos.entity.Harvest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HarvestDAOImpl implements HarvestDAO {

    @Override
    public ArrayList<Harvest> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Harvest> allHarvest = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM harvest");
        while (rst.next()) {
            Harvest harvest = new Harvest(rst.getString("harvest_no"), rst.getInt("qty"), rst.getDate("date").toLocalDate(),rst.getString("field_id"));
            allHarvest.add(harvest);
        }
        return allHarvest;
    }

    @Override
    public boolean add(Harvest entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO harvest (harvest_no,qty,date) VALUES (?,?,?)", entity.getHarvest_no(), entity.getQty(), entity.getDate());
    }

    @Override
    public boolean update(Harvest entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE harvest SET qty=?,  date=? WHERE harvest_no=?", entity.getQty(), entity.getDate());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT harvest_no FROM harvest WHERE harvest_no=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT harvest_no FROM harvest ORDER BY harvest_no DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("harvest_no");
            int newHarvestId = Integer.parseInt(id.replace("H00-", "")) + 1;
            return String.format("H00-%03d", newHarvestId);
        } else {
            return "H00-001";
        }
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM harvest WHERE harvest_no=?", id);
    }


    @Override
    public Harvest search(String harvest_no) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM harvest WHERE harvest_no=?", harvest_no + "");
        rst.next();
        return new Harvest(harvest_no + "", rst.getInt("qty"), rst.getDate("date").toLocalDate(),rst.getString("field_id"));
    }

}
