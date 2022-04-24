package com.vicras.blog.abac.domain.rule;

import static org.apache.logging.log4j.util.Strings.EMPTY;

import com.vicras.abaclib.engine.model.condition.Condition;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.meta.Information;
import com.vicras.blog.abac.domain.DomainFactory;
import com.vicras.blog.abac.domain.advice.LoggingAdviceProvider;
import com.vicras.blog.abac.domain.attribute.BlogAttributes;
import com.vicras.blog.model.Position;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogRule {

    private final BlogAttributes attributes;
    private final LoggingAdviceProvider adviceProvider;
    private final DomainFactory factory;

    public Rule isPosition(Position position) {
        var title = "Is " + position.getTitle();
        var description = "Check if the actor is " + position.getTitle();
        Information info = Information.of(title, description);

        Condition userIsManager = (prov) -> position.equals(prov.get(attributes.position()));
        Advice logToFile = adviceProvider.logAllResultToFile();
        return factory.getRule(info, userIsManager, List.of(logToFile));
    }

    public Rule timeFromTo(LocalTime since, LocalTime till) {
        var title = "Is time in from " + since + " to " + till;
        Information info = Information.of(title, EMPTY);
        Advice logToFile = adviceProvider.logAllResultToFile();

        Condition userIsManager = (prov) -> {
            var now = prov.get(attributes.timeNow());
            return since.isBefore(now) && now.isBefore(till);
        };
        return factory.getRule(info, userIsManager, List.of(logToFile));
    }

    public Rule onlyDocumentOwner() {
        var title = "Is document owner";
        Information info = Information.of(title, EMPTY);
        Advice logToFile = adviceProvider.logAllResultToFile();

        Condition userIsOwner = (prov) -> {
            var user = prov.get(attributes.user());
            var doc = prov.get(attributes.document());
            return doc.getCreator().equals(user);
        };

        return factory.getRule(info, userIsOwner, List.of(logToFile));
    }

    public Rule sameDepartment() {
        var title = "Is Same department of user and document";
        Information info = Information.of(title, EMPTY);
        Advice logToFile = adviceProvider.logAllResultToFile();

        Condition userIsOwner = (prov) -> {
            var user = prov.get(attributes.user());
            var doc = prov.get(attributes.document());
            return doc.getCreator().getDepartment().equals(user.getDepartment());
        };

        return factory.getRule(info, userIsOwner, List.of(logToFile));
    }

    public Rule notApprovedDocument() {
        var title = "Not approved state";
        Information info = Information.of(title, EMPTY);
        Advice logToFile = adviceProvider.logAllResultToFile();

        Condition userIsOwner = (prov) -> !prov.get(attributes.document()).isApproved();

        return factory.getRule(info, userIsOwner, List.of(logToFile));
    }
}
