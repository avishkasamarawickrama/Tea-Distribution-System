package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.employeeDTO;
import lk.ijse.pos.dto.fieldsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FieldBO extends SuperBO {
    ArrayList<fieldsDTO> getAllFields() throws SQLException, ClassNotFoundException;

    boolean addField(fieldsDTO dto) throws SQLException, ClassNotFoundException ;

    boolean updateField(fieldsDTO dto) throws SQLException, ClassNotFoundException ;

    boolean existField(String id) throws SQLException, ClassNotFoundException;

    boolean deleteField(String id) throws SQLException, ClassNotFoundException;

    String generateNewFieldID() throws SQLException, ClassNotFoundException;
}
