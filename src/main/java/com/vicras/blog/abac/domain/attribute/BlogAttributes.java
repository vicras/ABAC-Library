package com.vicras.blog.abac.domain.attribute;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.attribute.impl.StringAttributeImpl;
import com.vicras.blog.model.Action;
import com.vicras.blog.model.CommonUser;
import com.vicras.blog.model.Document;
import com.vicras.blog.model.Position;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class BlogAttributes {
    public Attribute<Position> position() {
        return StringAttributeImpl.of("worker.position", Position.class);
    }

    public Attribute<CommonUser> user() {
        return StringAttributeImpl.of("user", CommonUser.class);
    }

    public Attribute<Document> document() {
        return StringAttributeImpl.of("document", Document.class);
    }

    public Attribute<Action> documentAction() {
        return StringAttributeImpl.of("action.document", Action.class);
    }

    public Attribute<Boolean> documentActionExist() {
        return StringAttributeImpl.of("action.document.exist", Boolean.class);
    }

    public Attribute<Boolean> userActionExist() {
        return StringAttributeImpl.of("action.user.exist", Boolean.class);
    }

    public Attribute<Boolean> viewActionExist() {
        return StringAttributeImpl.of("action.view.exist", Boolean.class);
    }

    public Attribute<LocalTime> timeNow() {
        return StringAttributeImpl.of("env.time", LocalTime.class);
    }
}
