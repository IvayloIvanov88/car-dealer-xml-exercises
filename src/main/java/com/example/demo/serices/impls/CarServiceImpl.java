package com.example.demo.serices.impls;

import com.example.demo.models.dtos.seeds.CarSeedDto;
import com.example.demo.models.dtos.views.CarViewDto;
import com.example.demo.models.dtos.views.CarViewRootDto;
import com.example.demo.models.entities.Car;
import com.example.demo.repositories.CarRepository;
import com.example.demo.serices.CarService;
import com.example.demo.serices.PartService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartService partService;
    private final Random random;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, PartService partService, Random random) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partService = partService;
        this.random = random;
    }

    @Override
    public void seedCars(List<CarSeedDto> carSeedDtos) {
        carSeedDtos.forEach(carSeedDto -> {
            if (this.validationUtil.isValid(carSeedDto)) {
                if (this.carRepository.findByMakeAndModelAndTravelledDistance
                        (carSeedDto.getMake(), carSeedDto.getModel(), carSeedDto.getTravelledDistance()) == null) {
                    Car car = this.modelMapper.map(carSeedDto, Car.class);
                    car.setParts(this.partService.getRandomPart());
                    this.carRepository.saveAndFlush(car);
                } else {
                    System.out.printf("There is same car %s %s", carSeedDto.getMake(), carSeedDto.getModel());
                }
            } else {
                validationUtil.violations(carSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });
    }

    @Override
    public Car getRandomCar() {
        long randomId = this.random.nextInt((int) this.carRepository.count()) + 1L;
        return this.carRepository.getReferenceById(randomId);
    }

    @Override
    public long getCarsCount() {
        return this.carRepository.count();
    }

    @Override
    public CarViewRootDto writeCarsFromMakeToyota() {
        CarViewRootDto carViewRootDto = new CarViewRootDto();

        List<CarViewDto> carViewDto= this.carRepository.findByMakeOrderByModelAscThenByDistanceDesc("Toyota")
                .stream()
                .map(car -> this.modelMapper.map(car, CarViewDto.class))
                .collect(Collectors.toList());
        carViewRootDto.setCarViewDto(carViewDto);

        return carViewRootDto;
    }
}
