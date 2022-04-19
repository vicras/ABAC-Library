package com.vicras.abaclib.use.abac.domain.policy;

import static com.vicras.abaclib.use.model.Action.CREATE_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.DELETE_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.EDIT_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.VIEW_DOCUMENT;

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
public class UseDocumentPolicy {

    private final UseAttributes attr;
    private final UseRule rules;

    public Policy documentCreation() {
        Target createDocument = (prov) -> CREATE_DOCUMENT.equals(prov.get(attr.documentAction()));

        var managerRule = rules.userManager();
        var workTime = rules.workTime();

        return getPolicy(createDocument, List.of(managerRule, workTime));
    }

    public Policy documentEdition() {
        Target editDocument = (prov) -> EDIT_DOCUMENT.equals(prov.get(attr.documentAction()));

        var documentOwner = rules.onlyDocumentOwner();
        var workTime = rules.workTime();

        return getPolicy(editDocument, List.of(documentOwner, workTime));
    }

    public Policy documentView() {
        Target viewDocument = (prov) -> VIEW_DOCUMENT.equals(prov.get(attr.documentAction()));

        var exceptCEO = rules.exceptCEO();
        var workTime = rules.workTime();

        return getPolicy(viewDocument, List.of(exceptCEO, workTime));
    }

    public Policy documentDeletion() {
        Target deleteDoc = (prov) -> DELETE_DOCUMENT.equals(prov.get(attr.documentAction()));

        var documentOwner = rules.onlyDocumentOwner();
        var workTime = rules.workTime();

        return getPolicy(deleteDoc, List.of(documentOwner, workTime));
    }

    public Policy getPolicy(Target editDocument, List<Rule> rules) {
        Policy policy = new Policy();
        policy.setTarget(editDocument);
        policy.setCombinationRule(new AllowIfAllAllows());
        policy.setRules(rules);
        return policy;
    }
}
