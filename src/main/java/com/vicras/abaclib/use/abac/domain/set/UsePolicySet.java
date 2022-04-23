package com.vicras.abaclib.use.abac.domain.set;

import com.vicras.abaclib.engine.model.combinator.impl.AllowIfOneAllows;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.main.model.PolicySet;
import com.vicras.abaclib.use.abac.domain.advice.UseAdviceProvider;
import com.vicras.abaclib.use.abac.domain.attribute.UseAttributes;
import com.vicras.abaclib.use.abac.domain.policy.UseDocumentPolicy;
import com.vicras.abaclib.use.abac.domain.policy.UseUserPolicy;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsePolicySet {

    private final UseDocumentPolicy useDocumentPolicy;
    private final UseAdviceProvider adviceProvider;
    private final UseUserPolicy useUserPolicy;
    private final UseAttributes attributes;

    public PolicySet document() {
        var create = useDocumentPolicy.documentCreation();
        var delete = useDocumentPolicy.documentDeletion();
        var edit = useDocumentPolicy.documentEdition();
        var view = useDocumentPolicy.documentView();
        List<PolicyModel> policies = List.of(create, delete, edit, view);
        Target docAction = (prov) -> prov.get(attributes.documentActionExist());

        return getPolicy(
                policies,
                docAction,
                List.of(adviceProvider.adviceForExecutionSetPolicy()));
    }

    public PolicySet user() {
        var adminUserManagementPolicy = useUserPolicy.adminUserManagementPolicy();

        List<PolicyModel> policies = List.of(adminUserManagementPolicy);
        Target docAction = (prov) -> prov.get(attributes.userActionExist());

        return getPolicy(
                policies,
                docAction,
                List.of(adviceProvider.adviceNotForExecutionSetPolicy()));
    }

    private PolicySet getPolicy(
            List<PolicyModel> policies,
            Target docAction,
            List<Advice> advices) {
        PolicySet policySet = new PolicySet();
        policySet.setTarget(docAction);
        policySet.setPolicies(policies);
        policySet.setAdvices(advices);
        policySet.setCombinationRule(new AllowIfOneAllows());
        return policySet;
    }
}
