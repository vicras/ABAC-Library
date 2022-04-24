package com.vicras.blog.abac.config;

import com.vicras.abaclib.engine.pdp.extractor.PolicyProvider;
import com.vicras.abaclib.engine.pdp.extractor.impl.InMemoryPolicyProvider;
import com.vicras.blog.abac.domain.set.BlogPolicySet;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PdpConfig {

    private final BlogPolicySet blogPolicySet;

    @Bean
    public PolicyProvider policyProvider() {
        return new InMemoryPolicyProvider(blogPolicySet);
    }
}
