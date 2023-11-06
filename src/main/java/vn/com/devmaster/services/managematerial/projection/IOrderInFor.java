package vn.com.devmaster.services.managematerial.projection;

import java.util.Date;

public interface IOrderInFor {
    Integer getId();
    String getIdOrder();
    Date getDate();
    String getNamePayment();
    String getNameTranSport();
    Double getTotalMoney();
    String getNameReciver();
    String getAddress();
    String getPhone();
    Integer getStatus();

}
