package com.vicras.abaclib.use.abac.aspect.pointcut;

import com.vicras.abaclib.use.abac.aspect.annotation.AbacSecure;

import org.aspectj.lang.annotation.Pointcut;

public class AbacUsePointcuts {

    //    @Pointcut("execution(* com.vicras.abaclib.use.service.DocumentService.*(..)) &&"
//            + "! execution(* com.vicras.abaclib.use.service.DocumentService.getDocument(..))")
    @Pointcut(value = "@annotation(abacSecure)")
    public void abacSecure(AbacSecure abacSecure) {
    }
}
