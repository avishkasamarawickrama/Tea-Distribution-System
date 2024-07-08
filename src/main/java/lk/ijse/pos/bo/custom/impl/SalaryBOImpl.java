package lk.ijse.pos.bo.custom.impl;
import lk.ijse.pos.bo.custom.SalaryBO;
import lk.ijse.pos.dao.custom.SalaryDAO;
import lk.ijse.pos.dto.salaryDTO;
import lk.ijse.pos.entity.salary;
import java.sql.SQLException;
import java.util.ArrayList;
import lk.ijse.pos.dao.DAOFactory;

public class SalaryBOImpl implements SalaryBO {


    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);
    @Override
    public ArrayList<salaryDTO> getAllSalary() throws SQLException, ClassNotFoundException {
        ArrayList<salaryDTO> allSalary= new ArrayList<>();
        ArrayList<salary> all = salaryDAO.getAll();
        for (salary s : all) {
            allSalary.add(new salaryDTO(s.getNo(),s.getAmount(),s.getDate(),s.getEmployee_id()));
        }
        return allSalary;
    }

    @Override
    public boolean addSalary(salaryDTO dto) throws SQLException, ClassNotFoundException {
        return salaryDAO.add(new salary(dto.getNo(), dto.getAmount(), dto.getDate(), dto.getEmployee_id()));
    }

    @Override
    public boolean updateSalary (salaryDTO dto) throws SQLException, ClassNotFoundException {
        return salaryDAO.update(new salary(dto.getNo(), dto.getAmount(), dto.getDate(), dto.getEmployee_id()));
    }

    @Override
    public boolean existSalary(String id) throws SQLException, ClassNotFoundException {
        return salaryDAO.exist(id);
    }

    @Override
    public boolean deleteSalary(String id) throws SQLException, ClassNotFoundException {
        return salaryDAO.delete(id);
    }

    @Override
    public String generateNewSalaryID() throws SQLException, ClassNotFoundException {
        return salaryDAO.generateNewID();
    }
}
