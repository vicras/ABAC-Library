package com.vicras.abaclib.use.abac.domain.rule;

import static com.vicras.abaclib.engine.model.RuleEffect.PERMIT;

import com.vicras.abaclib.engine.model.condition.Condition;
import com.vicras.abaclib.engine.model.condition.impl.TrueCheck;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.use.abac.domain.advice.UseAdviceProvider;
import com.vicras.abaclib.use.abac.domain.attribute.UseAttributes;
import com.vicras.abaclib.use.model.Position;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UseRule {

    private final UseAttributes attributes;
    private final UseAdviceProvider adviceProvider;

    public Rule userManager() {
        Condition userIsManager = (prov) -> Position.MANAGER.equals(prov.get(attributes.position()));
        return ruleByTarget(userIsManager, List.of(adviceProvider.adviceForExecution()));
    }

    public Rule workTime() {
        Condition userIsManager = (prov) -> {
            var startTime = prov.get(attributes.workStartTime());
            var endTime = prov.get(attributes.workEndTime());
            var now = prov.get(attributes.timeNow());
            return startTime.isBefore(now) && now.isBefore(endTime);
        };
        return ruleByTarget(userIsManager, List.of(adviceProvider.adviceForExecution()));
    }

    public Rule onlyDocumentOwner() {
        Condition userIsOwner = (prov) -> {
            var user = prov.get(attributes.user());
            var doc = prov.get(attributes.document());
            return doc.getCreator().equals(user);
        };
        return ruleByTarget(userIsOwner, List.of(adviceProvider.adviceNotForExecution()));
    }

    public Rule exceptCEO() {
        Condition exceptCEO = (prov) -> !Position.CEO.equals(prov.get(attributes.position()));
        return ruleByTarget(exceptCEO, List.of(adviceProvider.adviceNotForExecution()));
    }

    public Rule userCeo() {
        Condition userIsManager = (prov) -> Position.CEO.equals(prov.get(attributes.position()));
        return ruleByTarget(userIsManager, List.of(adviceProvider.adviceNotForExecution()));
    }

    private Rule ruleByTarget(Condition userIsManager, List<Advice> advices) {
        Rule rule = new Rule();
        rule.setTarget(TrueCheck.get());
        rule.setCondition(userIsManager);
        rule.setEffect(PERMIT);
        rule.setAdvices(advices);
        return rule;
    }
}
