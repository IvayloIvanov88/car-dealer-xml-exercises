package com.example.demo.serices;

import com.example.demo.models.dtos.seeds.SupplierSeedDto;
import com.example.demo.models.entities.Supplier;

import java.util.List;

public interface SupplierService {

    void seedSuppliers(List<SupplierSeedDto> suppliedSeedDtos);

    Supplier getRandomSupplier();

}
