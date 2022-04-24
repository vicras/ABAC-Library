package com.vicras.blog.abac.domain.policy;

import static com.vicras.blog.model.Action.VIEW_AUTHOR;
import static com.vicras.blog.model.Action.VIEW_READER;
import static com.vicras.blog.model.Action.VIEW_REDACTOR;
import static com.vicras.blog.model.Position.AUTHOR;
import static com.vicras.blog.model.Position.READER;
import static com.vicras.blog.model.Position.REDACTOR;

import com.vicras.abaclib.engine.model.combinator.impl.AllowIfAllAllows;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.main.model.Policy;
import com.vicras.abaclib.engine.model.meta.Information;
import com.vicras.blog.abac.domain.DomainFactory;
import com.vicras.blog.abac.domain.advice.LoggingAdviceProvider;
import com.vicras.blog.abac.domain.attribute.BlogAttributes;
import com.vicras.blog.abac.domain.rule.BlogRule;
import com.vicras.blog.model.Action;
import com.vicras.blog.model.Position;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogViewPolicy {
    private final BlogRule rules;
    private final BlogAttributes attr;
    private final DomainFactory factory;
    private final LoggingAdviceProvider advice;

    public Policy author() {
        var title = "VIEW AUTHOR POLICY";
        var description = "Only author will get access";
        Information info = Information.of(title, description);
        return createPolicy(info, AUTHOR, VIEW_AUTHOR);
    }

    public Policy reader() {
        var title = "VIEW READER POLICY";
        var description = "Only reader will get access";
        Information info = Information.of(title, description);
        return createPolicy(info, READER, VIEW_READER);
    }

    public Policy redactor() {
        var title = "VIEW REDACTOR POLICY";
        var description = "Only redactor will get access";
        Information info = Information.of(title, description);
        return createPolicy(info, REDACTOR, VIEW_REDACTOR);
    }

    private Policy createPolicy(Information info, Position position, Action action) {
        Target positionTarget = (prov) -> action.equals(prov.get(attr.documentAction()));
        var redactor = rules.isPosition(position);

        return factory.getPolicy(
                info,
                positionTarget,
                List.of(redactor),
                List.of(advice.logAllResultToFile()),
                new AllowIfAllAllows()
        );
    }
}
