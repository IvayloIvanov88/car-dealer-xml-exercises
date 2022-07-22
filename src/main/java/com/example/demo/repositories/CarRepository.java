package com.example.demo.repositories;

import com.example.demo.models.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByMakeAndModelAndTravelledDistance(String name, String model, int distance);
    @Query("SELECT c FROM Car AS c WHERE c.make = ?1 ORDER BY c.model, c.travelledDistance Desc")
    List<Car> findByMakeOrderByModelAscThenByDistanceDesc(@Param("model") String model);
}
