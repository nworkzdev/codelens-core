package com.odyss.codelens.agent;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class InterceptorAdvice {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Origin Method method) {

        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Parameter[] parameters = method.getParameters();

        DataCapture.getInstance().captureEntry(className, methodName, parameters);
    }


    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void onExit(@Advice.Origin Method method,
                              @Advice.Return(typing = Assigner.Typing.DYNAMIC) Object ret,
                              @Advice.Thrown Throwable thrown) {


        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();


        if (thrown != null) {
            DataCapture.getInstance().captureExit(className, methodName, "Exception", thrown);
        } else {
            DataCapture.getInstance().captureExit(className, methodName, "Return", ret);
        }
    }
}