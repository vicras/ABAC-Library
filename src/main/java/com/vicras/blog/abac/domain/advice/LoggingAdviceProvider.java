package com.vicras.blog.abac.domain.advice;

import static com.vicras.abaclib.engine.model.result.CalculationResult.ALLOW;
import static java.lang.String.format;

import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.result.model.ResultModel;
import com.vicras.blog.abac.domain.log.FileLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingAdviceProvider {

    private final FileLogger fileLogger;

    public Advice logAllResultToFile() {
        Advice advice = new Advice();
        advice.setAction(this::logAction);
        advice.setEffectChecker(ALLOW::equals);
        return advice;
    }

    private void logAction(ResultModel resultModel) {
        var text = createText(resultModel);
        fileLogger.logInfo(text);
    }

    public String createText(ResultModel resultModel) {
        return format(
                "Model %s was executed with result %s",
                resultModel.getModel(),
                resultModel.getResult());
    }
}
