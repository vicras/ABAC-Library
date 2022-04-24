package com.vicras.blog.abac.config;

import com.vicras.abaclib.engine.pep.strategy.PDPResultConversionPolicy;
import com.vicras.abaclib.engine.pep.strategy.impl.DenyBasedStrategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PepConfig {
    //ContextExtractor

    @Bean
    public PDPResultConversionPolicy pepResultConversionPolicy() {
        return new DenyBasedStrategy();
    }
}
