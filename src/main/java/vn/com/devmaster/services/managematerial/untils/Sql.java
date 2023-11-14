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
    public static final String COUNT_ORDER_CUSTOMER = "select count(*) from orders s where s.IDCUSTOMER=:idcustomer";
    public static final String ORDER_INFOR = "select orders.ID as id, orders.IDORDERS as idOrder,ORDERS_DATE as date,pm.NAME as namePayment ,tm.NAME as nameTransport,orders.TOTAL_MONEY as totalMoney,orders.NAME_RECIVER as nameReciver,orders.ADDRESS as address ,orders.PHONE as phone,orders.STATUS as status\n" +
            " from orders\n" +
            "         left join orders_payment op on orders.ID = op.IDORD\n" +
            "         left join orders_transport ot on orders.ID = ot.IDORD\n" +
            "         left join payment_method pm on pm.ID = op.IDPAYMENT\n" +
            "         left join transport_method tm on tm.ID = ot.IDTRANSPORT\n" +
            "where orders.id=:idod";
    public static final String VIEW_ORDER_DETAIL = "select IDPRODUCT            as idProduct,\n" +
            "       p.NAME               as name,\n" +
            "       orders_details.QTY   as quantity,\n" +
            "       p.IMAGE              as image,\n" +
            "       orders_details.PRICE as price\n" +
            " from orders_details\n" +
            "         join product p on orders_details.IDPRODUCT = p.ID\n" +
            " where IDORD = :id";

    //Product
    public static final String UPDATE_QUANTITY_PRODUCT = "update product\n" +
            " set QUANTITY=QUANTITY + :qty\n" +
            " where ID = :idproduct\n";
    public static final String CART_BY_CUSTOMER = "select * from cart where id_customer=:id and status=1";
    public static final String UPDATE_QUANTITY_CART = "update cart set quantity=quantity+1 where id_customer=:id and id_product=:idpr and status=1";
    public static final String UPDATE_PRODUCT = "update product\n" +
            "set NAME=:name,\n" +
            "    DESCRIPTION=:description,\n" +
            "    NOTES=:notes,\n" +
            "    IMAGE=:urlImage,\n" +
            "    IDCATEGORY=:idcategory,\n" +
            "    PRICE=:price,\n" +
            "    UPDATED_DATE =:updated_date,\n" +
            "    ISACTIVE=:isactive\n" +
            "where ID = :id";
    public static final String ALL_ORDERS = "select * from orders ORDER BY ORDERS_DATE DESC";
    public static final String UPDATE_ORDER_STATUS = "update orders set STATUS=:status where ID=:idod";
    public static final String IMAGES_PRODUCT = "select*from product_images where ID_PRODUCT=:idproduct";
    public static final String UPDATE_CART_QUANTITY = "update cart\n" +
            "set quantity=:quantity\n" +
            "where id_customer = :id\n" +
            "  and id_product = :idproduct";
    public static  final String CUSTOMER_BY_USER_NAME="select *from customer where USERNAME=:username";
    public static final String REVENUE_BY_MONTH = "select sum(TOTAL_MONEY) as sum, (select month(ORDERS_DATE)) as month\n" +
            " from orders\n" +
            " group by (select month(ORDERS_DATE))\n" +
            " order by (select month(ORDERS_DATE)) ASC";
    public static final String REVENUE_BY_CATEGORY = "select sum(orders_details.PRICE * orders_details.QTY) as sum, c.NAME as name\n" +
            "\n" +
            "   from orders_details\n" +
            "         inner join product p on orders_details.IDPRODUCT = p.ID\n" +
            "         inner join category c on p.IDCATEGORY = c.ID\n" +
            "   group by c.NAME";
    public static final String REVENUE_BY_DAY="select sum(TOTAL_MONEY) as sum, CONVERT(ORDERS_DATE, DATE) as day\n" +
            " from orders\n" +
            " where MONTH(ORDERS_DATE) = :month\n" +
            " group by CONVERT(ORDERS_DATE, DATE)\n" +
            " order by CONVERT(ORDERS_DATE, DATE) ASC";

    public static final String MONTH_YEAR="select distinct MONTH(ORDERS_DATE) as month ,YEAR(ORDERS_DATE) as year\n" +
            "from orders\n" +
            "where YEAR(ORDERS_DATE)=:year\n" +
            "order by MONTH(ORDERS_DATE)";
    public static final String ORDER_DETAIL_BY_IDOD = "select*from orders_details where IDORD=:idod";
    public static final String UPDATE_PAYMENT = "update payment_method\n" +
            "set NAME=:namePayment,\n" +
            "    NOTES=:notes,\n" +
            "    UPDATED_DATE=:updatedDate,\n" +
            "    ISACTIVE=:paymentActive,\n" +
            "    PAYMENT_IMAGE=:urlImage\n" +
            "where ID = :idPayment";
}
