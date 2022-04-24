package com.vicras.blog.abac.aspect.pointcut;

import com.vicras.blog.abac.aspect.annotation.AbacSecure;

import org.aspectj.lang.annotation.Pointcut;

public class AbacUsePointcuts {

    @Pointcut(value = "@annotation(abacSecure)")
    public void abacSecure(AbacSecure abacSecure) {
    }
}
