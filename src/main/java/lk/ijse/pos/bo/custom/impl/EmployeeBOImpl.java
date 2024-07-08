package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.EmployeeBO;
import lk.ijse.pos.dao.DAOFactory;

import lk.ijse.pos.dao.custom.EmployeeDAO;

import lk.ijse.pos.dto.employeeDTO;

import lk.ijse.pos.entity.employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {


    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<employeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<employeeDTO> allEmployee= new ArrayList<>();
        ArrayList<employee> all = employeeDAO.getAll();
        for (employee e : all) {
            allEmployee.add(new employeeDTO(e.getEmployee_id(),e.getName(),e.getContact(),e.getStatus()));
        }
        return allEmployee;
    }

    @Override
    public boolean addEmployee(employeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new employee(dto.getEmployee_id(), dto.getName(), dto.getContact(), dto.getStatus()));
    }

    @Override
    public boolean updateEmployee (employeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new employee(dto.getEmployee_id(), dto.getName(), dto.getContact(), dto.getStatus()));
    }

    @Override
    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.exist(id);
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }


}
