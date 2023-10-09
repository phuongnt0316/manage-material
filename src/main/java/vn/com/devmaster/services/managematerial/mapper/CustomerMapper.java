package vn.com.devmaster.services.managematerial.mapper;

import vn.com.devmaster.services.managematerial.DTO.CustomerDto;
import vn.com.devmaster.services.managematerial.domain.Customer;

import java.util.List;

public class CustomerMapper implements EntityMapper<Customer, CustomerDto> {
    @Override
    public Customer toEntity(CustomerDto customerDto) {
        return null;
    }

    @Override
    public List<Customer> toEntity(List<CustomerDto> d) {
        return null;
    }

    @Override
    public CustomerDto toDto(Customer customer) {
        return null;
    }

    @Override
    public List<CustomerDto> toDto(List<Customer> e) {
        return null;
    }
}
