package com.vicras.blog.abac.domain.set;

import static java.util.Collections.emptyList;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.main.model.PolicySet;
import com.vicras.abaclib.engine.model.meta.Information;
import com.vicras.blog.abac.domain.DomainFactory;
import com.vicras.blog.abac.domain.advice.LoggingAdviceProvider;
import com.vicras.blog.abac.domain.attribute.BlogAttributes;
import com.vicras.blog.abac.domain.policy.BlogDocumentEditPolicy;
import com.vicras.blog.abac.domain.policy.BlogDocumentPolicy;
import com.vicras.blog.abac.domain.policy.BlogUserPolicy;
import com.vicras.blog.abac.domain.policy.BlogViewPolicy;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogPolicySet {

    private final BlogViewPolicy viewPolicy;
    private final BlogUserPolicy blogUserPolicy;
    private final BlogDocumentPolicy documentPolicy;
    private final BlogDocumentEditPolicy documentEditPolicy;

    private final DomainFactory factory;
    private final BlogAttributes attributes;
    private final LoggingAdviceProvider adviceProvider;

    public PolicySet document() {
        var title = "DOCUMENT SET POLICY";
        var description = "Create, Delete, Edit, Approve, document action exist";
        Information info = Information.of(title, description);

        List<PolicyModel> policies = List.of(
                documentPolicy.documentCreation(),
                documentPolicy.documentDeletion(),
                documentPolicy.documentById(),
                documentPolicy.approveDocument(),
                documentEditing()
        );

        return getSet(info, attributes.documentActionExist(), policies);
    }

    public PolicySet documentEditing() {
        var title = "DOCUMENT EDIT POLICY";
        var description = "Editing document";
        Information info = Information.of(title, description);

        List<PolicyModel> policies = List.of(
                documentEditPolicy.documentEditionByAuthor(),
                documentEditPolicy.documentEditionByRedactor()
        );
        return getSet(info, attributes.documentActionExist(), policies);
    }

    public PolicySet user() {
        var title = "USER SET POLICY";
        var description = "user action";
        Information info = Information.of(title, description);

        List<PolicyModel> policies = List.of(blogUserPolicy.adminUserManagementPolicy());

        return getSet(info, attributes.userActionExist(), policies);
    }

    public PolicySet view() {
        var title = "VIEW SET POLICY";
        var description = "Rules of viewing docs";
        Information info = Information.of(title, description);

        List<PolicyModel> policies = List.of(
                viewPolicy.author(),
                viewPolicy.reader(),
                viewPolicy.redactor()
        );

        return getSet(info, attributes.viewActionExist(), policies);
    }

    private PolicySet getSet(
            Information info,
            Attribute<Boolean> targetAttr,
            List<PolicyModel> policies) {
        Target docAction = (prov) -> prov.get(targetAttr);
        return factory.getPolicySet(
                info,
                policies,
                docAction,
                List.of(adviceProvider.logAllResultToFile()),
                emptyList()
        );
    }
}
