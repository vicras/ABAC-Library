package com.vicras.abaclib.use.abac.domain.rule;

import static com.vicras.abaclib.engine.model.RuleEffect.PERMIT;

import com.vicras.abaclib.engine.model.condition.Condition;
import com.vicras.abaclib.engine.model.condition.impl.TrueCheck;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.use.abac.domain.attribute.UseAttributes;
import com.vicras.abaclib.use.model.Position;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UseRule {

    private final UseAttributes attributes;

    public Rule userManager() {
        Condition userIsManager = (prov) -> Position.MANAGER.equals(prov.get(attributes.position()));
        return ruleByTarget(userIsManager);
    }

    public Rule workTime() {
        Condition userIsManager = (prov) -> {
            var startTime = prov.get(attributes.workStartTime());
            var endTime = prov.get(attributes.workEndTime());
            var now = prov.get(attributes.timeNow());
            return startTime.isBefore(now) && now.isBefore(endTime);
        };
        return ruleByTarget(userIsManager);
    }

    public Rule onlyDocumentOwner() {
        Condition userIsOwner = (prov) -> {
            var user = prov.get(attributes.user());
            var doc = prov.get(attributes.document());
            return doc.getCreator().equals(user);
        };
        return ruleByTarget(userIsOwner);
    }

    public Rule exceptCEO() {
        Condition exceptCEO = (prov) -> !Position.CEO.equals(prov.get(attributes.position()));
        return ruleByTarget(exceptCEO);
    }

    public Rule userCeo() {
        Condition userIsManager = (prov) -> Position.CEO.equals(prov.get(attributes.position()));
        return ruleByTarget(userIsManager);
    }

    private Rule ruleByTarget(Condition userIsManager) {
        Rule rule = new Rule();
        rule.setTarget(TrueCheck.get());
        rule.setCondition(userIsManager);
        rule.setEffect(PERMIT);
        return rule;
    }
}
