package lk.ijse.pos.bo.custom;
import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.employeeDTO;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<employeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;

    boolean addEmployee(employeeDTO dto) throws SQLException, ClassNotFoundException ;

    boolean updateEmployee(employeeDTO dto) throws SQLException, ClassNotFoundException ;

    boolean existEmployee(String id) throws SQLException, ClassNotFoundException;

    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    String generateNewEmployeeID() throws SQLException, ClassNotFoundException;
}
