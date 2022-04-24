package com.vicras.blog.abac.pip;

import static com.vicras.blog.model.Action.ADD_USERS;
import static com.vicras.blog.model.Action.CREATE_DOCUMENT;
import static com.vicras.blog.model.Action.DELETE_DOCUMENT;
import static com.vicras.blog.model.Action.DELETE_USERS;
import static com.vicras.blog.model.Action.EDIT_DOCUMENT;
import static com.vicras.blog.model.Action.VIEW_AUTHOR;
import static com.vicras.blog.model.Action.VIEW_DOCUMENT_BY_ID;
import static com.vicras.blog.model.Action.VIEW_READER;
import static com.vicras.blog.model.Action.VIEW_REDACTOR;
import static com.vicras.blog.model.Action.VIEW_USERS;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.attribute.provider.ContextAttributeSupplier;
import com.vicras.abaclib.engine.model.context.ExecutionContext;
import com.vicras.blog.abac.domain.attribute.BlogAttributes;
import com.vicras.blog.model.Action;
import com.vicras.blog.model.CommonUser;
import com.vicras.blog.model.Document;
import com.vicras.blog.model.Position;
import com.vicras.blog.service.DocumentService;
import com.vicras.blog.service.UserService;
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
public class PipAttributeAttributeSupplier implements ContextAttributeSupplier {

    private final BlogAttributes attr;
    private final UserService userService;
    private final DocumentService documentService;

    private final static Set<Action> DOCUMENT_ACTIONS = Set.of(
            CREATE_DOCUMENT,
            VIEW_DOCUMENT_BY_ID,
            EDIT_DOCUMENT,
            DELETE_DOCUMENT);

    private static final Set<Action> USER_ACTIONS = Set.of(VIEW_USERS, DELETE_USERS, ADD_USERS);
    private static final Set<Action> VIEW_ACTIONS = Set.of(VIEW_AUTHOR, VIEW_READER, VIEW_REDACTOR);

    @Override
    public <T> Collection<T> getAttributes(Attribute<T> attribute, ExecutionContext context) {
        return ofNullable(getAttribute(attribute, context))
                .map(stream -> castList(stream, attribute.getAttributeClass()))
                .orElseGet(() -> context.getValue(attribute.getAttributeClass()));
    }

    private <T> Stream<?> getAttribute(Attribute<T> attribute, ExecutionContext context) {
        if (attr.position().equals(attribute)) {
            return getPosition(context);
        }
        if (attr.user().equals(attribute)) {
            return getUser(context);
        }
        if (attr.document().equals(attribute)) {
            return getDocument(context);
        }
        if (attr.documentActionExist().equals(attribute)) {
            return isDocAction(context);
        }
        if (attr.userActionExist().equals(attribute)) {
            return isUserAction(context);
        }
        if (attr.viewActionExist().equals(attribute)) {
            return isViewAction(context);
        }
        return null;
    }

    private <T> List<T> castList(Stream<?> list, Class<T> clazz) {
        return list.map(clazz::cast).collect(toList());
    }

    private Stream<Boolean> isViewAction(ExecutionContext context) {
        return Stream.of(VIEW_ACTIONS.containsAll(context.getValue(Action.class)));
    }

    private Stream<Boolean> isUserAction(
            ExecutionContext context) {
        return Stream.of(USER_ACTIONS.containsAll(context.getValue(Action.class)));
    }

    private Stream<Boolean> isDocAction(ExecutionContext context) {
        return Stream.of(DOCUMENT_ACTIONS.containsAll(context.getValue(Action.class)));
    }

    private <T> Stream<Document> getDocument(ExecutionContext context) {
        return context.getValue(Long.class).stream()
                .map(documentService::getDocument);
    }

    private <T> Stream<CommonUser> getUser(ExecutionContext context) {
        return getCommonUserStream(context);
    }

    private <T> Stream<Position> getPosition(ExecutionContext context) {
        return getCommonUserStream(context)
                .map(CommonUser::getPosition);
    }

    private Stream<CommonUser> getCommonUserStream(ExecutionContext context) {
        return context.getValue(UsernamePasswordAuthenticationToken.class).stream()
                .map(Principal::getName)
                .map(userService::findUserByLogin);
    }
}
