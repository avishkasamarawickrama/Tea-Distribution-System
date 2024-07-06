package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,EMPLOYEE,FIELD,HARVEST,INVENTORY,ORDERS,ORDERDETAILS,SALARY,QUERY_DAO
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new InventoryDAOImpl();
            case FIELD:
                return new FieldDAOImpl();
            case HARVEST:
                return new HarvestDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailsDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case QUERY_DAO:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }


}
