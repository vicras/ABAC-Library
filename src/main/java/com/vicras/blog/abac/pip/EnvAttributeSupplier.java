package com.vicras.blog.abac.pip;

import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.attribute.provider.AttributesSupplier;
import com.vicras.blog.abac.domain.attribute.BlogAttributes;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvAttributeSupplier implements AttributesSupplier {

    private final BlogAttributes attributes;

    @Override
    public <T> List<T> getAttributes(Attribute<T> in) {
        return choose(in)
                .map(res -> in.getAttributeClass().cast(res))
                .stream()
                .collect(toList());
    }

    private <T> Optional<Object> choose(Attribute<T> in) {
        if (in.equals(attributes.timeNow())) {
            return of(LocalTime.of(11, 0));
        }
        return Optional.empty();
    }
}
