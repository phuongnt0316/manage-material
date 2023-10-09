package vn.com.devmaster.services.managematerial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.devmaster.services.managematerial.domain.Customer;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.repository.MaterialRepository;
import vn.com.devmaster.services.managematerial.repository.ProductRepository;

import java.util.List;
@Service
public class MaterialService {
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    ProductRepository productRepository;
    public List<Customer> getAll(){
        List<Customer> customers=materialRepository.findAll();
        return customers;
    }
    public  List<Product> getProduct(){
        List<Product> products=productRepository.findAll();
        return products;
    }
}
