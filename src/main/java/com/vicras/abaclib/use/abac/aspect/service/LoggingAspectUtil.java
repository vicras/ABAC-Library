package com.vicras.abaclib.use.abac.aspect.service;

import static java.util.Arrays.stream;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;

@UtilityClass
public class LoggingAspectUtil {

    private static final String PARAM_DELIMITER = ", ";
    private static final String NAME_VALUE_DELIMITER = " : ";

    public static String getParametersWithValues(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        String methodName = pjp.getSignature().getName();
        return stream(pjp.getTarget().getClass().getMethods())
                .filter(method -> methodName.equals(method.getName()))
                .findFirst()
                .map(LoggingAspectUtil::getParameterNames)
                .map(names -> formatNamesAndValues(names, args))
                .orElse(Arrays.toString(args));
    }

    private static String formatNamesAndValues(List<String> names, Object[] args) {
        var ans = new StringBuilder();
        for (int i = 0; i < names.size() || i < args.length; i++) {
            ans
                    .append(names.get(i))
                    .append(NAME_VALUE_DELIMITER)
                    .append(args[i])
                    .append(PARAM_DELIMITER);
        }
        return ans.length() == 0
                ? ""
                : ans.substring(0, ans.length() - PARAM_DELIMITER.length());
    }

    private static List<String> getParameterNames(Method method) {
        return stream(method.getParameters())
                .filter(Parameter::isNamePresent)
                .map(Parameter::getName)
                .collect(Collectors.toList());
    }
}
