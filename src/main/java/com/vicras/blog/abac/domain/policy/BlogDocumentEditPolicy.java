package com.vicras.blog.abac.domain.policy;

import static com.vicras.blog.model.Action.EDIT_DOCUMENT;
import static com.vicras.blog.model.Position.REDACTOR;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.combinator.impl.AllowIfAllAllows;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.main.model.Policy;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.meta.Information;
import com.vicras.blog.abac.domain.DomainFactory;
import com.vicras.blog.abac.domain.advice.LoggingAdviceProvider;
import com.vicras.blog.abac.domain.attribute.BlogAttributes;
import com.vicras.blog.abac.domain.rule.BlogRule;
import com.vicras.blog.model.Action;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogDocumentEditPolicy {

    private final BlogAttributes attr;
    private final LoggingAdviceProvider advice;
    private final DomainFactory factory;
    private final BlogRule rules;

    public Policy documentEditionByAuthor() {
        var title = "DOCUMENT EDITION BY AUTHOR POLICY";
        var description = "Document owner can edit not approved document";
        Information info = Information.of(title, description);

        var documentOwner = rules.onlyDocumentOwner();
        var notApprovedDocument = rules.notApprovedDocument();
        List<Rule> rules = List.of(documentOwner, notApprovedDocument);

        return createPolicy(info, rules, EDIT_DOCUMENT, new AllowIfAllAllows());
    }

    public Policy documentEditionByRedactor() {
        var title = "DOCUMENT EDITION BY REDACTOR POLICY";
        var description = "REDACTOR with same department can edit not approved document";
        Information info = Information.of(title, description);

        var redactor = rules.isPosition(REDACTOR);
        var sameDepartment = rules.sameDepartment();
        var notApprovedDocument = rules.notApprovedDocument();
        List<Rule> rules = List.of(redactor, sameDepartment, notApprovedDocument);

        return createPolicy(info, rules, EDIT_DOCUMENT, new AllowIfAllAllows());
    }

    private Policy createPolicy(
            Information info, List<Rule> rules, Action action,
            Combinator<Rule> combinator) {
        Target positionTarget = (prov) -> action.equals(prov.get(attr.documentAction()));

        return factory.getPolicy(
                info,
                positionTarget,
                rules,
                List.of(advice.logAllResultToFile()),
                combinator
        );
    }
}
