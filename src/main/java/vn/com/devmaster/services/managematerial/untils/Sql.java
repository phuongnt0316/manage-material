package vn.com.devmaster.services.managematerial.untils;

public class Sql {
    public static final String PRODUCT_BY_ID = "select * from product s where s.id =:idpr";
    public static final String CUSTOMER_LOGIN = "select * from customer where USERNAME=:user and PASSWORD=:pass";
    public static final String CART_BY_ID_CUSTOMER = "select p.ID as idProduct, p.NAME as name, s.quantity as quantity, p.IMAGE as image, p.PRICE as price\n" +
            " from cart s\n" +
            " left join product p on s.id_product = p.ID\n" +
            " where s.id_customer = :idcustomer and s.status=1";
    public static final String DELETE_CART = "update cart set status=0 where id_product=:delete_idpr and id_customer=:idcustomer";
    public static final String CART_BY_ID = "select p.ID as idProduct, s.quantity as quantity, p.PRICE as price\n" +
            " from cart s\n" +
            "         left join product p on s.id_product = p.ID\n" +
            " where s.id_customer = :idcustomer\n" +
            "  and s.status = 1";
}
