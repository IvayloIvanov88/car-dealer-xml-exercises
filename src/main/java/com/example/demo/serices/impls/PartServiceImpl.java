package com.example.demo.serices.impls;

import com.example.demo.models.dtos.seeds.PartSeedDto;
import com.example.demo.models.entities.Part;
import com.example.demo.repositories.PartRepository;
import com.example.demo.serices.PartService;
import com.example.demo.serices.SupplierService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SupplierService supplierService;
    private final Random random;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, SupplierService supplierService, Random random) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierService = supplierService;
        this.random = random;
    }

    @Override
    public void seedParts(List<PartSeedDto> partSeedDtos) {
        partSeedDtos.forEach(partSeedDto -> {
            if (this.validationUtil.isValid(partSeedDto)) {
                if (this.partRepository.findByNameAndPrice(partSeedDto.getName(), partSeedDto.getPrice()) == null) {
                    Part part = this.modelMapper.map(partSeedDto, Part.class);
                    part.setSupplier(this.supplierService.getRandomSupplier());
                    this.partRepository.saveAndFlush(part);
                } else {
                    System.out.printf("There is already part with name- %s in DB", partSeedDto.getName());
                }
            } else {
                this.validationUtil.violations(partSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.err::println);
            }
        });

    }

    @Override
    public Set<Part> getRandomPart() {
        Set<Part> resultSet = new HashSet<>();
        int randomCounter = this.random.nextInt(11) + 10;
        for (int i = 0; i < randomCounter; i++) {
            long randomId = this.random.nextInt((int) this.partRepository.count()) + 1;
            resultSet.add(this.partRepository.getReferenceById(randomId));
        }
        return resultSet;
    }
}
