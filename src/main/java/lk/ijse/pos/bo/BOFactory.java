package lk.ijse.pos.bo;

import lk.ijse.pos.bo.custom.impl.*;


public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,EMPLOYEE,FIELD,HARVEST,INVENTORY,PO,SALARY
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case FIELD:
                return new FieldBOImpl();
            case HARVEST:
                return new HarvestBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
                case PO:
                return (SuperBO) new PlaceOrderBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            default:
                return null;
        }
    }

}
