package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.FieldBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.FieldDAO;
import lk.ijse.pos.dto.fieldsDTO;
import lk.ijse.pos.entity.fields;
import java.sql.SQLException;
import java.util.ArrayList;

public class FieldBOImpl implements FieldBO {


    FieldDAO fieldDAO = (FieldDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FIELD);
    @Override
    public ArrayList<fieldsDTO> getAllFields() throws SQLException, ClassNotFoundException {
        ArrayList<fieldsDTO> allField= new ArrayList<>();
        ArrayList<fields> all = fieldDAO.getAll();
        for (fields f : all) {
            allField.add(new fieldsDTO(f.getField_id(),f.getName(),f.getAddress()));
        }
        return allField;
    }

    @Override
    public boolean addField(fieldsDTO dto) throws SQLException, ClassNotFoundException {
        return fieldDAO.add(new fields(dto.getField_id(), dto.getName(), dto.getAddress()));
    }

    @Override
    public boolean updateField (fieldsDTO dto) throws SQLException, ClassNotFoundException {
        return fieldDAO.update(new fields(dto.getField_id(), dto.getName(), dto.getAddress()));
    }

    @Override
    public boolean existField(String id) throws SQLException, ClassNotFoundException {
        return fieldDAO.exist(id);
    }

    @Override
    public boolean deleteField(String id) throws SQLException, ClassNotFoundException {
        return fieldDAO.delete(id);
    }

    @Override
    public String generateNewFieldID() throws SQLException, ClassNotFoundException {
        return fieldDAO.generateNewID();
    }


}
