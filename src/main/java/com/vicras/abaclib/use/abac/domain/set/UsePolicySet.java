package com.vicras.abaclib.use.abac.domain.set;

import com.vicras.abaclib.engine.model.combinator.impl.IfOneAllow;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.main.model.PolicySet;
import com.vicras.abaclib.use.abac.domain.attribute.UseAttributes;
import com.vicras.abaclib.use.abac.domain.policy.UsePolicy;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsePolicySet {

    private final UsePolicy usePolicy;
    private final UseAttributes attributes;

    public PolicySet document() {
        var create = usePolicy.documentCreation();
        var delete = usePolicy.documentDeletion();
        var edit = usePolicy.documentEdition();
        var view = usePolicy.documentSelection();
        List<PolicyModel> policies = List.of(create, delete, edit, view);
        Target docAction = (prov) -> prov.get(attributes.documentActionExist());

        return getPolicy(policies, docAction);
    }

    private PolicySet getPolicy(List<PolicyModel> policies, Target docAction) {
        PolicySet policySet = new PolicySet();
        policySet.setTarget(docAction);
        policySet.setPolicies(policies);
        policySet.setCombinationRule(new IfOneAllow());
        return policySet;
    }
}
