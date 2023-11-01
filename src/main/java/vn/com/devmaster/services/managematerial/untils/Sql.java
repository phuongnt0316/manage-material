package vn.com.devmaster.services.managematerial.untils;

public class Sql {
    public static final String PRODUCT_BY_ID = "select * from product s where s.id =:idpr";
    public static final String CUSTOMER_LOGIN = "select * from customer where USERNAME=:user and PASSWORD=:pass";
    public static final String CART_BY_ID_CUSTOMER = "select p.ID as idProduct, p.NAME as name, s.quantity as quantity, p.IMAGE as image, p.PRICE as price\n" +
            " from cart s\n" +
            " left join product p on s.id_product = p.ID\n" +
            " where s.id_customer = :idcustomer and s.status=1";
    public static final String DELETE_CART = "update cart set status=0 where id_product=:delete_idpr and id_customer=:idcustomer";
    public static final String BUY_CART = "update cart set status=2 where id_product=:delete_idpr and id_customer=:idcustomer";
    public static final String CART_BY_ID = "select p.ID as idProduct, s.quantity as quantity, p.PRICE as price\n" +
            " from cart s\n" +
            "         left join product p on s.id_product = p.ID\n" +
            " where s.id_customer = :idcustomer\n" +
            "  and s.status = 1";
    public static  final  String COUNT_ORDER_CUSTOMER="select count(*) from orders s where s.IDCUSTOMER=:idcustomer";
    public static final String ORDER_INFOR="select orders.IDORDERS as id,ORDERS_DATE as date,pm.NAME as namePayment ,tm.NAME as nameTransport,orders.TOTAL_MONEY as totalMoney,orders.NAME_RECIVER as nameReciver,orders.ADDRESS as address ,orders.PHONE as phone,orders.STATUS as status\n" +
            " from orders\n" +
            "         left join orders_payment op on orders.ID = op.IDORD\n" +
            "         left join orders_transport ot on orders.ID = ot.IDORD\n" +
            "         left join payment_method pm on pm.ID = op.IDPAYMENT\n" +
            "         left join transport_method tm on tm.ID = ot.IDTRANSPORT\n" +
            "where orders.id=:idod";
    public static final String VIEW_ORDER_DETAIL="select IDPRODUCT            as idProduct,\n" +
            "       p.NAME               as name,\n" +
            "       orders_details.QTY   as quantity,\n" +
            "       p.IMAGE              as image,\n" +
            "       orders_details.PRICE as price\n" +
            " from orders_details\n" +
            "         join product p on orders_details.IDPRODUCT = p.ID\n" +
            " where IDORD = :id";
}
