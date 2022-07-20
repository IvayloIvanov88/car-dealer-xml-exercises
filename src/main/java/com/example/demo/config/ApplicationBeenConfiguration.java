package com.example.demo.config;


import com.example.demo.utils.XMLParser;
import com.example.demo.utils.impls.ValidationUtilImpl;
import com.example.demo.utils.impls.XMLParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class ApplicationBeenConfiguration {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XMLParser xmlParser() {
        return new XMLParserImpl();
    }

    @Bean
    public ValidationUtilImpl validationUtilImpl(){
        return new ValidationUtilImpl();
    }
    @Bean
    public Random random(){
        return new Random();
    }
}
