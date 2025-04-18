package com.odyss.codelens.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class CodeLensAgent {

    public static void agentmain(String agentArgs, Instrumentation inst){
        new AgentBuilder.Default()
                .ignore(ElementMatchers.isSynthetic())
                .or(ElementMatchers.nameStartsWith("net.bytebuddy."))
                .or(ElementMatchers.nameStartsWith("sun."))
                .or(ElementMatchers.nameStartsWith("com.sun."))
                .or(ElementMatchers.nameStartsWith("jdk."))
                .or(ElementMatchers.nameStartsWith("com.odyss.codelens"))
                .type(ElementMatchers.any())
                .transform((builder, typeDescription, classLoader, javaModule, protectionDomain) -> {
                    return builder.method(ElementMatchers.any())
                            .intercept(Advice.to(InterceptorAdvice.class));
                })
                .installOn(inst);
    }

    public static void premain(String agentArgs, Instrumentation inst){
        agentmain(agentArgs, inst);
    }
}
