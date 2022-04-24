package com.vicras.blog.abac.domain;

import static com.vicras.abaclib.engine.model.RuleEffect.PERMIT;
import static java.util.Collections.emptyList;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.combinator.impl.AllowIfOneAllows;
import com.vicras.abaclib.engine.model.condition.Condition;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.condition.impl.TrueCheck;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.main.model.Policy;
import com.vicras.abaclib.engine.model.main.model.PolicySet;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.meta.Information;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DomainFactory {
    public Policy getPolicy(
            Information info, Target editDocument, List<Rule> rules, List<Advice> advices,
            Combinator<Rule> combinator) {
        Policy policy = new Policy();

        policy.setTitle(info.getTitle());
        policy.setDescription(info.getDescription());

        policy.setTarget(editDocument);

        policy.setRules(rules);
        policy.setCombinationRule(combinator);

        policy.setAdvices(advices);
        policy.setObligations(emptyList());

        return policy;
    }

    public Rule getRule(Information info, Condition condition, List<Advice> advices) {
        Rule rule = new Rule();

        rule.setTitle(info.getTitle());
        rule.setDescription(info.getDescription());

        rule.setTarget(TrueCheck.get());
        rule.setCondition(condition);

        rule.setEffect(PERMIT);
        rule.setAdvices(advices);
        rule.setObligations(emptyList());
        return rule;
    }

    public PolicySet getPolicySet(
            Information info,
            List<PolicyModel> policies,
            Target docAction,
            List<Advice> advices,
            List<Obligation> obligations) {
        PolicySet policySet = new PolicySet();

        policySet.setTitle(info.getTitle());
        policySet.setDescription(info.getDescription());

        policySet.setTarget(docAction);

        policySet.setPolicies(policies);
        policySet.setCombinationRule(new AllowIfOneAllows());

        policySet.setAdvices(advices);
        policySet.setObligations(obligations);
        return policySet;
    }
}
