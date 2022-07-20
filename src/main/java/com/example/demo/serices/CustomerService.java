package com.example.demo.serices;

import com.example.demo.models.dtos.seeds.CustomerSeedDto;
import com.example.demo.models.dtos.views.CustomerViewRootDto;
import com.example.demo.models.entities.Customer;

import java.util.List;

public interface CustomerService {

    void seedCustomers(List<CustomerSeedDto> customersSeedDto);

    Customer getRandomCustomer();

    CustomerViewRootDto writeAllOrderedCustomer();
}
