package com.example.demo.serices.impls;

import com.example.demo.models.entities.Sale;
import com.example.demo.repositories.SaleRepository;
import com.example.demo.serices.CarService;
import com.example.demo.serices.CustomerService;
import com.example.demo.serices.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final Random random;


    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, Random random) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.random = random;
    }

    @Override
    public void seedSales() {
            int carCount = (int) this.carService.getCarsCount();
        for (int i = 0; i < carCount; i++) {
            Sale sale = new Sale();
            sale.setDiscount(this.setRandomDiscount());
            sale.setCar(this.carService.getRandomCar());
            sale.setCustomer(this.customerService.getRandomCustomer());
            this.saleRepository.saveAndFlush(sale);
        }
    }

    private double setRandomDiscount() {
        Double[] discounts = new Double[]{
                0D, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.4, 0.5};

        return discounts[this.random.nextInt(discounts.length)];
    }
}
