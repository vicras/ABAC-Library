package com.vicras.abaclib.use.abac.config;

import com.vicras.abaclib.engine.pip.reducer.AttributeValuesReducer;
import com.vicras.abaclib.engine.pip.reducer.impl.RandomAttributeValuesReducer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PipConfig {

    //AttributeProvider
    //AttributesProvider
    //AttributeWithContextProvider

    // PIP by default use RandomAttributeValuesReducer
    @Bean
    public AttributeValuesReducer pipValueReducer() {
        return new RandomAttributeValuesReducer();
    }
}
