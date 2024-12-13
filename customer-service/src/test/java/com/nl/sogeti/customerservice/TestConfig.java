package com.nl.sogeti.customerservice;

import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@TestConfiguration
public class TestConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
