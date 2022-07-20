package com.example.demo.serices.impls;

import com.example.demo.models.dtos.seeds.CustomerSeedDto;
import com.example.demo.models.dtos.views.CustomerViewDto;
import com.example.demo.models.dtos.views.CustomerViewRootDto;
import com.example.demo.models.entities.Customer;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.serices.CustomerService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Random random;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Random random) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.random = random;
    }

    @Override
    public void seedCustomers(List<CustomerSeedDto> customersSeedDto) {
        customersSeedDto.forEach(customerSeedDto -> {
            if (this.validationUtil.isValid(customerSeedDto)) {
                if (this.customerRepository.findByNameAndBirthDate(customerSeedDto.getName(), customerSeedDto.getBirthDate()) == null) {
                    this.customerRepository.saveAndFlush(this.modelMapper.map(customerSeedDto, Customer.class));
                } else {
                    System.out.printf("Customer with name: %s is already in DB", customerSeedDto.getName());
                }
            } else {
                this.validationUtil.violations(customerSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });
    }

    @Override
    public Customer getRandomCustomer() {
        long randomId = this.random.nextInt((int) this.customerRepository.count()) + 1L;
        return this.customerRepository.getReferenceById(randomId);
    }

    @Override
    public CustomerViewRootDto writeAllOrderedCustomer() {
        CustomerViewRootDto customerViewRootDto = new CustomerViewRootDto();

        List<CustomerViewDto> customerViewDtos = this.customerRepository.
                findAllByBirthDateAndIsYoungDriver()
                .stream()
                .map(c -> this.modelMapper.map(c,CustomerViewDto.class))
                .collect(Collectors.toList());

        customerViewRootDto.setCustomers(customerViewDtos);

        return customerViewRootDto;
    }

}
