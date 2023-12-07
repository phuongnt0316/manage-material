package vn.com.devmaster.services.managematerial.projection;

public interface IOrderInFor {
    Integer getId();
    String getIdOrder();
    String getDate();
    String getNamePayment();
    String getNameTranSport();
    Double getTotalMoney();
    String getNameReciver();
    String getAddress();
    String getPhone();
    Integer getStatus();
    String getNote();

}
