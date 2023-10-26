package vn.com.devmaster.services.managematerial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.mapper.CartMapper;
import vn.com.devmaster.services.managematerial.mapper.ProductMapper;
import vn.com.devmaster.services.managematerial.projection.IOrderDetailDTO;
import vn.com.devmaster.services.managematerial.projection.IViewCart;
import vn.com.devmaster.services.managematerial.repository.*;

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
    public List<Product> getProductByID(String idpr){
        List<Product> products=productRepository.getProductByID(idpr);
        return products;
    }
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public List<IViewCart> getCartByIdCustomer(String idcustomer) {
        List<IViewCart> carts=cartRepository.getCartByIdCustomer(idcustomer);
       return carts;
    }
    public List<Customer> getCustomerByID(String userName, String password){
       List<Customer> customers= customerRepository.getCustomerByID(userName,password);
        return customers;
    }

    public void saveProduct(ProductDto dto) {

            Product product=productMapper.toEntity(dto);
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
        return  new PageImpl<Product>(products,pageable,products.size());
    }

    public void deleteProductCarts(Integer delete_idpr, Integer idcustomer) {
       cartRepository.deleteProductCarts(delete_idpr,idcustomer);
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

    public void deleteProductCarts(List<OrdersDetail> ordersDetails, Integer id) {
        for(OrdersDetail ordersDetail:ordersDetails){
            deleteProductCarts(ordersDetail.getIdproduct(),id);
        }
    }
}
