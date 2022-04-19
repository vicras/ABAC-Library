package com.vicras.abaclib.use.abac.pip;

import static com.vicras.abaclib.use.model.Action.ADD_USERS;
import static com.vicras.abaclib.use.model.Action.CREATE_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.DELETE_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.DELETE_USERS;
import static com.vicras.abaclib.use.model.Action.EDIT_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.VIEW_DOCUMENT;
import static com.vicras.abaclib.use.model.Action.VIEW_USERS;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.attribute.provider.AttributeWithContextProvider;
import com.vicras.abaclib.engine.model.context.ExecutionContext;
import com.vicras.abaclib.use.abac.domain.attribute.UseAttributes;
import com.vicras.abaclib.use.model.Action;
import com.vicras.abaclib.use.model.CommonUser;
import com.vicras.abaclib.use.model.Document;
import com.vicras.abaclib.use.model.Position;
import com.vicras.abaclib.use.service.DocumentService;
import com.vicras.abaclib.use.service.UserService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PipAttributeProvider implements AttributeWithContextProvider {

    private final UseAttributes attr;
    private final UserService userService;
    private final DocumentService documentService;

    private final static Set<Action> DOCUMENT_ACTIONS = Set.of(
            CREATE_DOCUMENT,
            EDIT_DOCUMENT,
            DELETE_DOCUMENT,
            VIEW_DOCUMENT);

    private static final Set<Action> USER_ACTIONS = Set.of(VIEW_USERS, DELETE_USERS, ADD_USERS);

    @Override
    public <T> Collection<T> getAttributes(Attribute<T> attribute, ExecutionContext context) {
        if (attr.position().equals(attribute)) {
            return getPosition(attr.position(), context, attribute.getAttributeClass());
        }
        if (attr.user().equals(attribute)) {
            return getUser(attr.user(), context, attribute.getAttributeClass());
        }
        if (attr.document().equals(attribute)) {
            return getDocument(attr.document(), context, attribute.getAttributeClass());
        }
        if (attr.documentActionExist().equals(attribute)) {
            return isDocAction(attr.documentActionExist(), context, attribute.getAttributeClass());
        }
        if (attr.userActionExist().equals(attribute)) {
            return isUserAction(attr.documentActionExist(), context, attribute.getAttributeClass());
        }

        return context.getValue(attribute.getAttributeClass());
    }

    private <T> Collection<T> isUserAction(
            Attribute<Boolean> documentActionExist,
            ExecutionContext context,
            Class<T> attributeClass) {
        return Stream.of(USER_ACTIONS.containsAll(context.getValue(Action.class)))
                .map(attributeClass::cast)
                .collect(toList());
    }

    private <T> Collection<T> isDocAction(
            Attribute<Boolean> documentAction,
            ExecutionContext context,
            Class<T> attributeClass) {
        return Stream.of(DOCUMENT_ACTIONS.containsAll(context.getValue(Action.class)))
                .map(attributeClass::cast)
                .collect(toList());
    }

    private <T> Collection<T> getDocument(
            Attribute<Document> document,
            ExecutionContext context,
            Class<T> attributeClass) {
        return context.getValue(Long.class).stream()
                .map(documentService::getDocument)
                .map(attributeClass::cast)
                .collect(toList());
    }

    private <T> Collection<T> getUser(
            Attribute<CommonUser> user,
            ExecutionContext context,
            Class<T> attributeClass) {
        return getCommonUserStream(context)
                .map(attributeClass::cast)
                .collect(toList());
    }

    private <T> List<T> getPosition(
            Attribute<Position> attribute,
            ExecutionContext context,
            Class<T> tClass) {
        return getCommonUserStream(context)
                .map(CommonUser::getPosition)
                .map(tClass::cast)
                .collect(toList());
    }

    private Stream<CommonUser> getCommonUserStream(ExecutionContext context) {
        return context.getValue(UsernamePasswordAuthenticationToken.class).stream()
                .map(Principal::getName)
                .map(userService::findUserByLogin);
    }


}
