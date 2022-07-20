package com.example.demo.controllers;

import com.example.demo.constants.GlobalConstant;
import com.example.demo.models.dtos.seeds.CarSeedRootDto;
import com.example.demo.models.dtos.seeds.CustomerSeedRootDto;
import com.example.demo.models.dtos.seeds.PartSeedRootDto;
import com.example.demo.models.dtos.seeds.SuppliersSeedRootDto;
import com.example.demo.models.dtos.views.CustomerViewRootDto;
import com.example.demo.serices.*;
import com.example.demo.utils.XMLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Component
public class AppController implements CommandLineRunner {

    private final XMLParser xmlParser;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public AppController(XMLParser xmlParser, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.xmlParser = xmlParser;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {

//        this.seedSuppliers();
//        this.seedParts();
//        this.seedCars();
//        this.seedCustomers();
//        this.seedSales();
        this.orderedCustomers();

    }

    private void orderedCustomers() throws JAXBException {
        CustomerViewRootDto customerViewRootDto = this.customerService.writeAllOrderedCustomer();

        this.xmlParser.marshalToXML(GlobalConstant.ORDERED_CUSTOMERS,customerViewRootDto);
    }

    private void seedSales() {
        this.saleService.seedSales();
    }

    private void seedCustomers() throws JAXBException, FileNotFoundException {
        CustomerSeedRootDto customerSeedRootDto = this.xmlParser.unmarshalFromXML(GlobalConstant.CUSTOMERS_FILE_PATH, CustomerSeedRootDto.class);
        this.customerService.seedCustomers(customerSeedRootDto.getCustomers());
    }

    private void seedCars() throws JAXBException, FileNotFoundException {
        CarSeedRootDto carSeedRootDto = this.xmlParser.unmarshalFromXML(GlobalConstant.CARS_FILE_PATH, CarSeedRootDto.class);
        this.carService.seedCars(carSeedRootDto.getCars());
    }

    private void seedParts() throws JAXBException, FileNotFoundException {
        PartSeedRootDto partSeedRootDto = this.xmlParser
                .unmarshalFromXML(GlobalConstant.PARTS_FILE_PATH, PartSeedRootDto.class);

        this.partService.seedParts(partSeedRootDto.getParts());
    }

    private void seedSuppliers() throws JAXBException, FileNotFoundException {
        SuppliersSeedRootDto suppliersSeedRootDto = this.xmlParser
                .unmarshalFromXML(GlobalConstant.SUPPLIERS_FILE_PATH, SuppliersSeedRootDto.class);

        this.supplierService.seedSuppliers(suppliersSeedRootDto.getSuppliers());
    }

}
