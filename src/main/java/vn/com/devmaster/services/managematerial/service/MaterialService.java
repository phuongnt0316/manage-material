package vn.com.devmaster.services.managematerial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.mapper.CartMapper;
import vn.com.devmaster.services.managematerial.mapper.ProductMapper;
import vn.com.devmaster.services.managematerial.projection.IODPMTS;
import vn.com.devmaster.services.managematerial.projection.IOrderDetailDTO;
import vn.com.devmaster.services.managematerial.projection.IViewProduct;
import vn.com.devmaster.services.managematerial.repository.*;

import java.time.LocalDate;
import java.util.List;
@Service
public class MaterialService {
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    private TransportMethodRepository transportMethodRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrdersDetailRepository ordersDetailRepository;
    @Autowired
    private OrdersPaymentRepository ordersPaymentRepository;
    @Autowired
    private OrdersTransportRepository ordersTransportRepository;

    public List<Customer> getAll(){
        List<Customer> customers=materialRepository.findAll();
        return customers;
    }
    public  List<Product> getProduct(){
        List<Product> products=productRepository.getProduct();
        return products;
    }
    public  List<Product> getAllProduct(){
        List<Product> products=productRepository.findAll();
        return products;
    }
    public Product getProductByID(Integer idpr){
        Product product=productRepository.getProductByID(idpr);
        return product;
    }
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public List<IViewProduct> getCartByIdCustomer(Integer idcustomer) {
        List<IViewProduct> carts=cartRepository.getCartByIdCustomer(idcustomer);
       return carts;
    }
    public Customer getCustomerByID(String userName, String password){
       Customer customer= customerRepository.getCustomerByID(userName,password);
        return customer;
    }

    public void saveProduct(Product product) {
            productRepository.save(product);
    }

    public List<Category> getAllCategory() {
        List<Category> categories=categoryRepository.getAllCategory1();
        return categories;
    }

    public List<Product> getProductByCategory(String idcate) {
        List<Product> products=productRepository.getProductByCategory(idcate);
        return products;
    }
    public Page<Product> getAll(Integer pageNo) {


        Pageable pageable= PageRequest.of(pageNo-1,5);
        return this.productRepository.findAll(pageable);
    }
    public Page<Product> searchProduct(String keyword,Integer pageNo){
        List<Product> products=productRepository.searchProduct(keyword);
        Pageable pageable=PageRequest.of(pageNo-1,5);
        Integer start= (int) pageable.getOffset();
        Integer end= (pageable.getOffset()+pageable.getPageSize())>products.size()?products.size(): (int) pageable.getOffset()+pageable.getPageSize();
        products=products.subList(start,end);
        return  new PageImpl<Product>(products,pageable,productRepository.searchProduct(keyword).size());
    }

    public void deleteProductCarts(Integer delete_idpr, Integer idcustomer) {
       cartRepository.deleteProductCarts(delete_idpr,idcustomer);
    }
    public void BuyCarts(Integer idproduct, Integer idcustomer) {
        cartRepository.BuyCarts(idproduct,idcustomer);
    }


    public List<TransportMethod> getTransport() {
        return transportMethodRepository.getTransport();
    }

    public List<PaymentMethod> getPayment() {
        return paymentMethodRepository.getPayment();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<IOrderDetailDTO> getCartById(Integer idcustomer) {
       return cartRepository.getCartByID(idcustomer);
    }

    public void saveAll(List<OrdersDetail> ordersDetails) {
        ordersDetailRepository.saveAll(ordersDetails);
    }

    public void save(OrdersPayment ordersPayment) {
        ordersPaymentRepository.save(ordersPayment);
    }

    public void save(OrdersTransport ordersTransport) {
        ordersTransportRepository.save(ordersTransport);
    }

    public void BuyCarts(List<OrdersDetail> ordersDetails, Integer id) {
        for(OrdersDetail ordersDetail:ordersDetails){
            BuyCarts(ordersDetail.getIdproduct(),id);
        }
    }

    public List<Order> getOrderByCustomer(Integer id) {
        return orderRepository.getOrderByCustomer(id);
    }
    public String getOrderId(Integer idcustomer){
        LocalDate localDate = LocalDate.now();
        String date=localDate.getYear()+""+localDate.getMonth().getValue()+""+localDate.getDayOfMonth();
        int n=orderRepository.getCountByCustomer(idcustomer);
        String orderid=date+idcustomer+(n+1)+"";
        return orderid;
    }

    public IODPMTS getOrderInfor(Integer id) {
        return orderRepository.getorderInfor(id);
    }

    public List<IViewProduct> getOrderDetailByID(Integer id) {
        return ordersDetailRepository.getOrderDetailByID(id);
    }
    public void updateQuantityProduct(Integer idproduct,Integer qty){ // update quantity after order
        productRepository.updateQuantityProduct(idproduct,qty);
    }

    public List<Cart> getCartByCustomer(Integer id) {
        return cartRepository.getCartByCustomer(id);
    }

    public void updateCart(Integer id, Integer idpr) {
        cartRepository.updateCart(id,idpr);
    }

    public Page<Product> getProductByCategory(Integer category, Integer pageNo) {
        List<Product> products=productRepository.getProductByCategory(String.valueOf(category));
        Pageable pageable=PageRequest.of(pageNo-1,5);
        Integer start= (int) pageable.getOffset();
        Integer end= (pageable.getOffset()+pageable.getPageSize())>products.size()?products.size(): (int) pageable.getOffset()+pageable.getPageSize();
        products=products.subList(start,end);
        return  new PageImpl<Product>(products,pageable,products.size());
    }

    public  Page<Product> sortProduct(Integer sort, Integer pageNo) {
        List<Product> products=productRepository.sortProductByPriceASC();
        Pageable pageable=PageRequest.of(pageNo-1,5);
        Integer start= (int) pageable.getOffset();
        Integer end= (pageable.getOffset()+pageable.getPageSize())>products.size()?products.size(): (int) pageable.getOffset()+pageable.getPageSize();
        products=products.subList(start,end);
        return  new PageImpl<Product>(products,pageable,productRepository.findAll().size());
    }
    public boolean checkCart(List<Cart> carts, Integer id){
        boolean check=true;
        for (Cart cart:carts){
            if (cart.getIdProduct()==id) {
                check=false;
                break;
            }
        }
        return check;
    }
    public Double getTotalMoney(List <IViewProduct> carts) {
        Double sum=0.0;
        for(IViewProduct cart:carts){
            sum+=cart.getPrice();
        }
        return sum;
    }
    public Double getTotal(List <ViewCart> carts) {
        Double sum=0.0;
        for(ViewCart cart:carts){
            sum+=cart.getPrice();
        }
        return sum;
    }

}
