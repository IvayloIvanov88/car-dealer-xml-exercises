package com.example.demo.serices;

import com.example.demo.models.dtos.seeds.PartSeedDto;
import com.example.demo.models.entities.Part;

import java.util.List;
import java.util.Set;

public interface PartService {

    void seedParts(List<PartSeedDto> partSeedDtos);

    Set<Part> getRandomPart();
}
