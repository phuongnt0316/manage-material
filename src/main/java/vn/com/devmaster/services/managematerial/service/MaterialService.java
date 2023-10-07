package vn.com.devmaster.services.managematerial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.devmaster.services.managematerial.domain.Customer;
import vn.com.devmaster.services.managematerial.repository.MaterialRepository;

import java.util.List;
@Service
public class MaterialService {
    @Autowired
    MaterialRepository materialRepository;
    public List<Customer> getAll(){
        List<Customer> customers=materialRepository.findAll();
        return customers;
    }
}
