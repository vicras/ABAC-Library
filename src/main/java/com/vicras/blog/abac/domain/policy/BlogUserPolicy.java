package com.vicras.blog.abac.domain.policy;

import static com.vicras.blog.model.Position.REDACTOR;

import com.vicras.abaclib.engine.model.combinator.impl.AllowIfAllAllows;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.main.model.Policy;
import com.vicras.abaclib.engine.model.meta.Information;
import com.vicras.blog.abac.domain.DomainFactory;
import com.vicras.blog.abac.domain.advice.LoggingAdviceProvider;
import com.vicras.blog.abac.domain.attribute.BlogAttributes;
import com.vicras.blog.abac.domain.rule.BlogRule;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogUserPolicy {
    private final BlogRule rules;
    private final BlogAttributes attr;
    private final DomainFactory domainFactory;
    private final LoggingAdviceProvider adviceProvider;

    public Policy adminUserManagementPolicy() {
        Target createDocument = (prov) -> prov.get(attr.userActionExist());

        var title = "REDACTOR USER POLICY";
        var description = "Allow only if Redactor";
        Information info = Information.of(title, description);

        var managerRule = rules.isPosition(REDACTOR);

        return domainFactory.getPolicy(
                info,
                createDocument,
                List.of(managerRule),
                List.of(adviceProvider.logAllResultToFile()),
                new AllowIfAllAllows());
    }
}
