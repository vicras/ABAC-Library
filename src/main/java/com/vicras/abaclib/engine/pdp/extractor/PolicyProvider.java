package com.vicras.abaclib.engine.pdp.extractor;

import com.vicras.abaclib.engine.model.main.PolicyModel;

import java.util.List;

public interface PolicyProvider {
    List<PolicyModel> getAllPolicy();
}
