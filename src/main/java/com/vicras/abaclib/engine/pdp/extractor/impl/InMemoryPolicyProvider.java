package com.vicras.abaclib.engine.pdp.extractor.impl;

import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.pdp.extractor.PolicyProvider;
import com.vicras.blog.abac.domain.set.BlogPolicySet;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InMemoryPolicyProvider implements PolicyProvider {

    private final BlogPolicySet set;

    @Override
    public List<PolicyModel> getAllPolicy() {
        return List.of(set.document(), set.user(), set.view());
    }
}
