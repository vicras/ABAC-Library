package com.vicras.abaclib.use.abac.config;

import com.vicras.abaclib.engine.pdp.extractor.PolicyProvider;
import com.vicras.abaclib.engine.pdp.extractor.impl.InMemoryPolicyProvider;
import com.vicras.abaclib.use.abac.domain.set.UsePolicySet;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PdpConfig {

    private final UsePolicySet usePolicySet;

    @Bean
    public PolicyProvider policyProvider() {
        return new InMemoryPolicyProvider(usePolicySet);
    }
}
