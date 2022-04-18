package com.vicras.abaclib.use.abac.pip;

import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.attribute.provider.AttributesProvider;
import com.vicras.abaclib.use.abac.domain.attribute.UseAttributes;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvAttributeProvider implements AttributesProvider {

    private final UseAttributes attributes;

    @Override
    public <T> List<T> getAttributes(Attribute<T> in) {
        return choose(in)
                .map(res -> in.getAttributeClass().cast(res))
                .stream()
                .collect(toList());
    }

    private <T> Optional<Object> choose(Attribute<T> in) {
        if (in.equals(attributes.workStartTime())) {
            return of(LocalTime.of(8, 0));
        }
        if (in.equals(attributes.workEndTime())) {
            return of(LocalTime.of(18, 0));
        }
        if (in.equals(attributes.timeNow())) {
            return of(LocalTime.of(11, 0));
        }
        return Optional.empty();
    }
}
