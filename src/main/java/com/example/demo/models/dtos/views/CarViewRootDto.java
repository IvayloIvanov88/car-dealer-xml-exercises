package com.example.demo.models.dtos.views;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarViewRootDto {

    @XmlElement(name = "car")
    List<CarViewDto> carViewDto;

    public CarViewRootDto() {
    }

    public List<CarViewDto> getCarViewDto() {
        return carViewDto;
    }

    public void setCarViewDto(List<CarViewDto> carViewDto) {
        this.carViewDto = carViewDto;
    }
}
