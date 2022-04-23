package com.vicras.abaclib.engine.model.meta;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Information implements Informational{
    private final String title;
    private final String description;
}
