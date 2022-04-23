package com.vicras.abaclib.engine.model.main.model;

import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.ObjectModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor(access = PRIVATE)
public class EmptyModel implements ObjectModel {

    private static final EmptyModel model = new EmptyModel();

    private String title = EMPTY;
    private String description = EMPTY;

    private Target target = (ignore) -> false;
    private Collection<Obligation> obligations = emptyList();
    private Collection<Advice> advices = emptyList();

    public static EmptyModel get() {
        return model;
    }
}
