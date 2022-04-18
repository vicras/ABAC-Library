package com.vicras.abaclib.use.abac.aspect.service;

import static com.vicras.abaclib.use.abac.aspect.service.LoggingAspectUtil.getParametersWithValues;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.function.Function.identity;

import com.vicras.abaclib.engine.pep.PolicyEnforcementPoint;
import com.vicras.abaclib.engine.pep.context.ContextInputValue;
import com.vicras.abaclib.engine.pep.context.impl.DefaultInputValue;
import com.vicras.abaclib.use.abac.aspect.annotation.AbacSecure;
import com.vicras.abaclib.use.exception.exceptions.internal.InternalException;
import com.vicras.abaclib.use.exception.exceptions.security.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.stream.Stream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AspectService {

    private final PolicyEnforcementPoint pip;
    private final ApplicationContext context;

    public Object checkRights(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Controller: {} invoked", pjp.getSignature().toShortString());
        log.info("Arguments: [{}]", getParametersWithValues(pjp));
        return checkAuth(pjp);
    }

    private Object checkAuth(ProceedingJoinPoint pjp) throws Throwable {
        var method = getMethod(pjp);
        var inputParams = createContextInputParams(pjp.getArgs(), method);
        return pip.isAllowed(inputParams)
                ? invokeMethod(pjp)
                : throwAuthException();
    }

    private Method getMethod(ProceedingJoinPoint pjp) {
        String methodName = pjp.getSignature().getName();
        return stream(pjp.getTarget().getClass().getMethods())
                .filter(meth -> methodName.equals(meth.getName()))
                .findFirst()
                .orElseThrow(() -> new InternalException("can't recognize invoked method"));
    }

    private Object throwAuthException() {
        throw new ForbiddenException("Access forbidden");
    }

    private ContextInputValue<?>[] createContextInputParams(Object[] args, Method inMethod) {
        var inPrincipal = (context.getBean(Principal.class));
        var inAction = inMethod.getAnnotation(AbacSecure.class).value();
        var in = Stream.of(inMethod, inPrincipal, inAction).map(DefaultInputValue::of);
        return Stream.of(
                        in,
                        Arrays.stream(args).map(DefaultInputValue::of)
                )
                .flatMap(identity())
                .toArray(ContextInputValue[]::new);
    }

    private Object invokeMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        log.info("Result: {}", isNull(result) ? "void" : result);
        return result;
    }
}
