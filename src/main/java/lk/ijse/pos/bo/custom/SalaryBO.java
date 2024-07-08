package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.inventoryDTO;
import lk.ijse.pos.dto.salaryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SalaryBO extends SuperBO {

    ArrayList<salaryDTO> getAllSalary() throws SQLException, ClassNotFoundException;

    boolean addSalary(salaryDTO dto) throws SQLException, ClassNotFoundException ;

    boolean updateSalary(salaryDTO dto) throws SQLException, ClassNotFoundException ;

    boolean existSalary(String id) throws SQLException, ClassNotFoundException;

    boolean deleteSalary(String id) throws SQLException, ClassNotFoundException;

    String generateNewSalaryID() throws SQLException, ClassNotFoundException;
}
