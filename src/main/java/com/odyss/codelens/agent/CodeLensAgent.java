package com.odyss.codelens.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.List;

public class CodeLensAgent {

    private static final List<String> DEFAULT_IGNORED_PACKAGES = Arrays.asList(
            "sun.",
            "com.sun.",
            "jdk."
    );

    public static void agentmain(String agentArgs, Instrumentation inst) {
        configureAgent(agentArgs, inst);
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        configureAgent(agentArgs, inst);
    }

    private static void configureAgent(String agentArgs, Instrumentation inst) {
        AgentBuilder agentBuilder = new AgentBuilder.Default()
                .ignore(ElementMatchers.isSynthetic());

        AgentBuilder.Ignored orAgentBuilder = agentBuilder.ignore(ElementMatchers.nameStartsWith("net.bytebuddy."));
        for (String packageName : DEFAULT_IGNORED_PACKAGES) {
            orAgentBuilder = orAgentBuilder.or(ElementMatchers.nameStartsWith(packageName));
        }

        AgentBuilder.Identified.Narrowable narrowableAgentBuilder = orAgentBuilder.type(ElementMatchers.any());
        if (agentArgs != null && !agentArgs.trim().isEmpty()) {
            narrowableAgentBuilder = narrowableAgentBuilder.and(ElementMatchers.nameStartsWith(agentArgs.trim()));
        }
        narrowableAgentBuilder.transform((builder, typeDescription, classLoader, javaModule, protectionDomain) -> builder.method(ElementMatchers.any())
                        .intercept(Advice.to(InterceptorAdvice.class)))
                .installOn(inst);
    }
}