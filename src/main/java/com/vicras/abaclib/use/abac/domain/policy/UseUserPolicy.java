package com.vicras.abaclib.use.abac.domain.policy;

import com.vicras.abaclib.engine.model.combinator.impl.AllowIfAllAllows;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.main.model.Policy;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.use.abac.domain.attribute.UseAttributes;
import com.vicras.abaclib.use.abac.domain.rule.UseRule;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UseUserPolicy {
    private final UseAttributes attr;
    private final UseRule rules;


    public Policy adminUserManagementPolicy() {
        Target createDocument = (prov) -> prov.get(attr.userActionExist());

        var managerRule = rules.userCeo();
        var workTime = rules.workTime();

        return getPolicy(createDocument, List.of(managerRule, workTime));
    }


    public Policy getPolicy(Target editDocument, List<Rule> rules) {
        Policy policy = new Policy();
        policy.setTarget(editDocument);
        policy.setCombinationRule(new AllowIfAllAllows());
        policy.setRules(rules);
        return policy;
    }
}
