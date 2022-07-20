package com.example.demo.serices;

import com.example.demo.models.dtos.seeds.CarSeedDto;
import com.example.demo.models.entities.Car;

import java.util.List;

public interface CarService {
    void seedCars(List<CarSeedDto> carSeedDtos);

    Car getRandomCar();
    long getCarsCount();
}
