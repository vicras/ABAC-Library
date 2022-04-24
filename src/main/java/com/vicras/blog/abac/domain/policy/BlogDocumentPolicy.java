package com.vicras.blog.abac.domain.policy;

import static com.vicras.blog.model.Action.APPROVE_DOCUMENT;
import static com.vicras.blog.model.Action.CREATE_DOCUMENT;
import static com.vicras.blog.model.Action.DELETE_DOCUMENT;
import static com.vicras.blog.model.Action.VIEW_DOCUMENT_BY_ID;
import static com.vicras.blog.model.Position.AUTHOR;
import static com.vicras.blog.model.Position.REDACTOR;
import static java.time.LocalTime.of;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.combinator.impl.AllowIfAllAllows;
import com.vicras.abaclib.engine.model.combinator.impl.AllowIfTwoAllows;
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
public class BlogDocumentPolicy {

    private final BlogAttributes attr;
    private final LoggingAdviceProvider advice;
    private final DomainFactory factory;
    private final BlogRule rules;

    public Policy documentCreation() {
        var title = "DOCUMENT CREATION POLICY";
        var description = "Position is Author, 8.00 - 13.00";
        Information info = Information.of(title, description);

        var managerRule = rules.isPosition(AUTHOR);
        var workTime = rules.timeFromTo(of(8, 0), of(13, 0));
        List<Rule> rules = List.of(managerRule, workTime);

        return createPolicy(info, rules, CREATE_DOCUMENT, new AllowIfAllAllows());
    }

    public Policy documentById() {
        var title = "DOCUMENT BY ID POLICY";
        var description = "Document owner or REDACTOR with same department can view document by id";
        Information info = Information.of(title, description);
        List<Rule> rules = RedactorWithSameDepartmentOrOwner();
        return createPolicy(info, rules, VIEW_DOCUMENT_BY_ID, new AllowIfTwoAllows());
    }

    public Policy documentDeletion() {

        var title = "DOCUMENT DELETE POLICY";
        var description = "Document owner or REDACTOR can delete document";
        Information info = Information.of(title, description);

        List<Rule> rules = RedactorWithSameDepartmentOrOwner();
        return createPolicy(info, rules, DELETE_DOCUMENT, new AllowIfTwoAllows());
    }

    public Policy approveDocument() {
        var title = "DOCUMENT APPROVE POLICY";
        var description = "REDACTOR with same department can approve document from 13.00 - 18.00";
        Information info = Information.of(title, description);

        var redactor = rules.isPosition(REDACTOR);
        var sameDepartment = rules.sameDepartment();
        var workTime = rules.timeFromTo(of(8, 0), of(13, 0));
        List<Rule> rules = List.of(redactor, sameDepartment, workTime);

        return createPolicy(info, rules, APPROVE_DOCUMENT, new AllowIfAllAllows());
    }

    private List<Rule> RedactorWithSameDepartmentOrOwner() {
        var documentOwner = rules.onlyDocumentOwner();
        var redactor = rules.isPosition(REDACTOR);
        var sameDepartment = rules.sameDepartment();
        return List.of(redactor, sameDepartment, documentOwner);
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
