package vn.com.devmaster.services.managematerial.untils;

public class Sql {
    public static final String PRODUCT_BY_ID = "select * from product s where s.id =:idpr";
    public static final String CUSTOMER_LOGIN = "select * from customer where USERNAME=:user and PASSWORD=:pass";
    public static final String CART_BY_ID_CUSTOMER = "select p.ID       as idProduct,\n" +
            "       p.NAME     as name,\n" +
            "       s.quantity as quantityCart,\n" +
            "       p.IMAGE    as image,\n" +
            "       p.PRICE    as price,\n" +
            "       p.QUANTITY as quantityProduct,\n" +
            "       p.ISACTIVE as isactive,\n" +
            "       p.SALE     as sale\n" +
            "from cart s\n" +
            "         left join product p on s.id_product = p.ID\n" +
            "where s.id_customer = :idcustomer\n" +
            "  and s.status = 1";
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
            "       orders_details.QTY   as quantityCart,\n" +
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
            "    ISACTIVE=:isactive, UPDATED_BY=:customerId, sale=:sale\n" +
            "where ID = :id";
    public static final String ALL_ORDERS = "select * from orders ORDER BY ORDERS_DATE DESC";
    public static final String UPDATE_ORDER_STATUS = "update orders set STATUS=:status where ID=:idod";
    public static final String IMAGES_PRODUCT = "select*from product_images where ID_PRODUCT=:idproduct";
    public static final String UPDATE_CART_QUANTITY = "update cart\n" +
            "set quantity=:quantity\n" +
            "where id_customer = :id\n" +
            "  and id_product = :idproduct";
    public static final String CUSTOMER_BY_USER_NAME = "select *from customer where USERNAME=:username";
    public static final String CUSTOMER_BY_EMAIL = "select *from customer where EMAIL=:email";
    public static final String REVENUE_BY_MONTH = "select (sum(TOTAL_MONEY) - sum(ot.TOTAL)) as sum, (select month(ORDERS_DATE)) as month\n" +
            "from orders\n" +
            "         inner join orders_transport ot on orders.ID = ot.IDORD\n" +
            "where STATUS = 4\n" +
            "group by (select month(ORDERS_DATE))\n" +
            "order by (select month(ORDERS_DATE)) ASC";
    public static final String REVENUE_BY_CATEGORY = "select sum(orders_details.PRICE * orders_details.QTY) as sum, c.NAME as name\n" +
            "from orders_details\n" +
            "         inner join product p on orders_details.IDPRODUCT = p.ID\n" +
            "         inner join category c on p.IDCATEGORY = c.ID\n" +
            "         inner join orders o on orders_details.IDORD = o.ID\n" +
            "where o.STATUS=4\n" +
            "group by c.NAME\n";
    public static final String REVENUE_BY_DAY = "select sum(TOTAL_MONEY) as sum, CONVERT(ORDERS_DATE, DATE) as day\n" +
            " from orders\n" +
            " where MONTH(ORDERS_DATE) = :month\n" +
            " group by CONVERT(ORDERS_DATE, DATE)\n" +
            " order by CONVERT(ORDERS_DATE, DATE) ASC";

    public static final String MONTH_YEAR = "select distinct MONTH(ORDERS_DATE) as month ,YEAR(ORDERS_DATE) as year\n" +
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
    public static final String TOTAL_REVENUE = "select(sum(PRICE*QTY))\n" +
            "from orders_details inner join orders o on orders_details.IDORD = o.ID\n" +
            "where o.STATUS=4 and MONTH(o.ORDERS_DATE)= :month";
    public static final String GET_BLOG = "SELECT *\n" +
            "FROM blog\n" +
            "where ISACTIVE = 1\n" +
            "order by CREATED_DATE DESC\n" +
            "limit 3";
    public static final String GET_RECEIVED = "select product.ID, (product.QUANTITY - a.sum) as quantity\n" +
            "from (select ID_PRODUCT, sum(product_received.QUANTITY) as sum\n" +
            "      from product_received\n" +
            "      where (to_days(now()) - TO_DAYS(RECEIVED_DATE)) < 180\n" +
            "      group by ID_PRODUCT) a\n" +
            "         inner join product on a.ID_PRODUCT = product.ID\n" +
            "where product.QUANTITY - a.sum > 0\n" +
            "union\n" +
            "select product.ID, product.QUANTITY\n" +
            "from product\n" +
            "         inner join product_received pr on product.ID = pr.ID_PRODUCT\n" +
            "where product.ID not in (select product.ID as quantity180\n" +
            "                         from (select ID_PRODUCT, sum(product_received.QUANTITY) as sum\n" +
            "                               from product_received\n" +
            "                               where (to_days(now()) - TO_DAYS(RECEIVED_DATE)) < 180\n" +
            "                               group by ID_PRODUCT) a\n" +
            "                                  inner join product on a.ID_PRODUCT = product.ID\n" +
            "                         where product.QUANTITY - a.sum > 0)\n" +
            "  and (to_days(now()) - TO_DAYS(RECEIVED_DATE)) >= 180\n" +
            "group by (ID)\n" +
            "order by ID ASC";
    public static final String GET_RECEIVED_BY_INVENTORY = "select *\n" +
            "from product_received\n" +
            "where to_days(now()) - TO_DAYS(RECEIVED_DATE) >= 180\n" +
            "order by RECEIVED_DATE DESC";
    public static final String GET_RECEIVED_BY_ID = "\n" +
            "select *\n" +
            "from product_received\n" +
            "where to_days(now()) - TO_DAYS(RECEIVED_DATE) >= 180 and ID_PRODUCT=:id\n" +
            "order by RECEIVED_DATE DESC";
    public static final String UPDATE_PASSWORD_BY_EMAIL = "update customer set PASSWORD= :password where EMAIL= :email";
    public static final String PRODUCT_SALE="select *from product where SALE>0.0\n" +
            "order by  SALE DESC";
    public static final String SALE_PRODUCTS = "select product.ID as id, NAME as name, DESCRIPTION as description, IMAGE as image, PRICE as price, product.QUANTITY as quantity, SALE as sale\n" +
            "    from\n" +
            "(select product.ID, (product.QUANTITY - a.sum) as quantity\n" +
            "from (select ID_PRODUCT, sum(product_received.QUANTITY) as sum\n" +
            "      from product_received\n" +
            "      where (to_days(now()) - TO_DAYS(RECEIVED_DATE)) < 180\n" +
            "      group by ID_PRODUCT) a\n" +
            "         inner join product on a.ID_PRODUCT = product.ID\n" +
            "where product.QUANTITY - a.sum > 0\n" +
            "union\n" +
            "select product.ID, product.QUANTITY\n" +
            "from product\n" +
            "         inner join product_received pr on product.ID = pr.ID_PRODUCT\n" +
            "where product.ID not in (select product.ID as quantity180\n" +
            "                         from (select ID_PRODUCT, sum(product_received.QUANTITY) as sum\n" +
            "                               from product_received\n" +
            "                               where (to_days(now()) - TO_DAYS(RECEIVED_DATE)) < 180\n" +
            "                               group by ID_PRODUCT) a\n" +
            "                                  inner join product on a.ID_PRODUCT = product.ID\n" +
            "                         where product.QUANTITY - a.sum > 0)\n" +
            "  and (to_days(now()) - TO_DAYS(RECEIVED_DATE)) >= 180\n" +
            "group by (ID)) c left join product on c.ID = product.ID\n" +
            "    where product.ISACTIVE=1\n" +
            "order by product.PRICE ASC";
    public static final String SEARCH_PRODUCT = "select *\n" +
            "from product s\n" +
            "where s.NAME like concat('%', :keyword, '%')\n" +
            "  and s.ISACTIVE = 1\n" +
            "and s.PRICE*(1.0-s.SALE) >=:priceProduct1 and s.PRICE*(1.0-s.SALE)<= :priceProduct2\n" +
            "and s.IDCATEGORY IN :idCates";
    public static final String GET_VIEW_ORDER = "select orders.ID           as id,\n" +
            "       orders.IDORDERS     as idOrder,\n" +
            "       DATE_FORMAT(ORDERS_DATE,'%e/%c/%Y %H:%i:%s')         as date,\n" +
            "       pm.NAME             as namePayment,\n" +
            "       tm.NAME             as nameTransport,\n" +
            "       orders.TOTAL_MONEY  as totalMoney,\n" +
            "       orders.NAME_RECIVER as nameReciver,\n" +
            "       orders.ADDRESS      as address,\n" +
            "       orders.PHONE        as phone,\n" +
            "       orders.STATUS       as status,\n" +
            "       orders.NOTES as note\n" +
            "from orders\n" +
            "         left join orders_payment op on orders.ID = op.IDORD\n" +
            "         left join orders_transport ot on orders.ID = ot.IDORD\n" +
            "         left join payment_method pm on pm.ID = op.IDPAYMENT\n" +
            "         left join transport_method tm on tm.ID = ot.IDTRANSPORT\n" +
            "order by ORDERS_DATE desc";
    public static final String STATISTICAL_PRODUCT = "select  p.ID as id,p.NAME as name,sum(orders_details.QTY) as quantity\n" +
            "from orders_details\n" +
            "         inner join product p on orders_details.IDPRODUCT = p.ID\n" +
            "         inner join category c on p.IDCATEGORY = c.ID\n" +
            "         inner join orders o on orders_details.IDORD = o.ID\n" +
            "where o.STATUS=4\n" +
            "group by p.NAME,p.ID\n" +
            "order by quantity desc";
    public static final String STATISTICAL_PRODUCT1 = "select  p.ID as id,p.NAME as name,sum(orders_details.QTY) as quantity\n" +
            "from orders_details\n" +
            "         inner join product p on orders_details.IDPRODUCT = p.ID\n" +
            "         inner join category c on p.IDCATEGORY = c.ID\n" +
            "         inner join orders o on orders_details.IDORD = o.ID\n" +
            "where o.STATUS=4 and p.ISACTIVE=1\n" +
            "group by p.NAME,p.ID\n" +
            "order by quantity desc";
    public static final String UPDATE_CATEGORY = "update category\n" +
            "set NAME=:name,\n" +
            "    IMAGE=:urlImage,\n" +
            "    NOTES=:notes,\n" +
            "    UPDATED_DATE=:date,\n" +
            "    ISACTIVE=:isActive,\n" +
            "    UPDATED_BY=:customerId\n" +
            "where ID = :id";
    public static final String UPDATE_PRODUCT_ISACTIVE = "update product set isactive=0 where idcategory=:id";
    public static final String GET_BLOG_RELATED = "select *\n" +
            "from blog\n" +
            "order by created_date DESC\n" +
            "limit 3";
}
