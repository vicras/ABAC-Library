package com.vicras.abaclib.engine.model.main.model;

import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;

import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor(access = PRIVATE)
public class EmptyModel implements BaseModel {

    private static final EmptyModel model = new EmptyModel();

    private Target target = (ignore) -> false;
    private Collection<Obligation> obligations = emptyList();
    private Collection<Advice> advices = emptyList();

    public static EmptyModel get() {
        return model;
    }
}
