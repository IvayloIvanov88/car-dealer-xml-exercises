package com.example.demo.serices.impls;

import com.example.demo.models.dtos.seeds.SupplierSeedDto;
import com.example.demo.models.entities.Supplier;
import com.example.demo.repositories.SupplierRepository;
import com.example.demo.serices.SupplierService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private Random random;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Random random) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.random = random;
    }

    @Override
    public void seedSuppliers(List<SupplierSeedDto> supplierSeedDtos) {
        supplierSeedDtos.forEach(supplierSeedDto -> {
            if (this.validationUtil.isValid(supplierSeedDto)) {
                if (this.supplierRepository.findByName(supplierSeedDto.getName()) == null) {
                    // след като му направим идвете валидации, дали го има в базата и дали е преминало през нашите валидации
                    // с моделМапъра го мапваме към ентити и го записваме в базата
                    this.supplierRepository.saveAndFlush(this.modelMapper.map(supplierSeedDto, Supplier.class));
                } else {
                    System.out.printf("Supplier %s is already in DB ", supplierSeedDto.getName());
                }
            } else {
                this.validationUtil.violations(supplierSeedDto)
                        .stream().map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });

    }

    @Override
    public Supplier getRandomSupplier() {
        long randomId = this.random.nextInt((int) this.supplierRepository.count()) + 1;
        return this.supplierRepository.getReferenceById(randomId);
    }
}
