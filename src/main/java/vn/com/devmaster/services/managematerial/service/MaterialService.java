package vn.com.devmaster.services.managematerial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.devmaster.services.managematerial.DTO.CartDto;
import vn.com.devmaster.services.managematerial.domain.Cart;
import vn.com.devmaster.services.managematerial.domain.Category;
import vn.com.devmaster.services.managematerial.domain.Customer;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.mapper.CartMapper;
import vn.com.devmaster.services.managematerial.repository.CartRepository;
import vn.com.devmaster.services.managematerial.repository.CategoryRepository;
import vn.com.devmaster.services.managematerial.repository.MaterialRepository;
import vn.com.devmaster.services.managematerial.repository.ProductRepository;

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
    public List<Category> allCategory1() {
        List<Category> categories = categoryRepository.allCategory1();

        return categories;
    }
    public List<Product> getProductByID(String idpr){
        List<Product> products=productRepository.getProductByID(idpr);
        return products;
    }
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

}
