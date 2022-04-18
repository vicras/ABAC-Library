package com.vicras.abaclib.use.abac.domain.attribute;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.attribute.impl.StringAttributeImpl;
import com.vicras.abaclib.use.model.Action;
import com.vicras.abaclib.use.model.CommonUser;
import com.vicras.abaclib.use.model.Document;
import com.vicras.abaclib.use.model.Position;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class UseAttributes {
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

    public Attribute<LocalTime> workStartTime() {
        return StringAttributeImpl.of("env.work.time.begin", LocalTime.class);
    }

    public Attribute<LocalTime> workEndTime() {
        return StringAttributeImpl.of("env.work.time.end", LocalTime.class);
    }

    public Attribute<LocalTime> timeNow() {
        return StringAttributeImpl.of("env.time", LocalTime.class);
    }
}
