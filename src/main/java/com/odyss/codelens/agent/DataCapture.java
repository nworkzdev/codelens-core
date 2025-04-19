package com.odyss.codelens.agent;

import com.odyss.codelens.call.MethodCall;
import com.odyss.codelens.call.Variable;

import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.Arrays;

public class DataCapture {

    private static DataCapture instance;
    private final ThreadLocal<MethodCall> currentMethodCall = new ThreadLocal<>();

    private DataCapture() {}

    public static DataCapture getInstance() {
        if (instance == null) {
            instance = new DataCapture();
        }
        return instance;
    }

    public void captureEntry(String className, String methodName, Parameter[] args) {
        var parameters = Arrays.stream(args)
                .map(arg -> Variable.fromObject(arg.getName(), arg, LocalDateTime.now()))
                .toList();
        currentMethodCall.set(new MethodCall(methodName, className, parameters));
    }

    public void captureExit(String className, String methodName, String type, Object value) {
        MethodCall methodCall = currentMethodCall.get();
        if (methodCall != null) {
            methodCall.setEndTime(LocalDateTime.now());
            methodCall.setReturnedValue(Variable.fromObject("returnValue", value, LocalDateTime.now()));
            currentMethodCall.remove();
        }
    }
}