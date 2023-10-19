package vn.com.devmaster.services.managematerial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.domain.Cart;
import vn.com.devmaster.services.managematerial.domain.Category;
import vn.com.devmaster.services.managematerial.domain.Customer;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.mapper.CartMapper;
import vn.com.devmaster.services.managematerial.mapper.ProductMapper;
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

    public List<Cart> getCartByIdCustomer(Integer idcustomer) {
        idcustomer=2;
        List<Cart> carts=cartRepository.getCartByIdCustomer(idcustomer);
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
}
