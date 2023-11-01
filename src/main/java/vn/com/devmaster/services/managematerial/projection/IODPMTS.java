package vn.com.devmaster.services.managematerial.projection;

import java.util.Date;

public interface IODPMTS {
    String getId();
    Date getDate();
    String getNamePayment();
    String getNameTranSport();
    Double getTotalMoney();
    String getNameReciver();
    String getAddress();
    String getPhone();
    Integer getStatus();

}
