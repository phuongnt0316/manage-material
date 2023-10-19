package vn.com.devmaster.services.managematerial.untils;

public class Sql {
    public static final String PRODUCT_BY_ID="select * from product s where s.id =:idpr";
    public static final String CUSTOMER_BY_ID="select * from cart s where s.id_customer =:idcustomer";
    public static final String CUSTOMER_LOGIN="select * from customer where USERNAME=:user and PASSWORD=:pass";
}
