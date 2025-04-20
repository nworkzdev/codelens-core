package com.odyss.codelens.agent;

import com.odyss.codelens.call.MethodCall;
import com.odyss.codelens.call.Variable;
import com.odyss.codelens.event.CallGraphEventManager;
import com.odyss.codelens.event.EventManager;

import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.Arrays;

public class DataCapture {

    private final ThreadLocal<MethodCall> currentMethodCall = new ThreadLocal<>();

    private static class Holder {
        static final DataCapture INSTANCE = new DataCapture();
    }

    private DataCapture() {
    }

    public static DataCapture getInstance() {
        return Holder.INSTANCE;
    }

    public void captureEntry(String className, String methodName, Parameter[] args) {
        var parameters = Arrays.stream(args)
                .map(arg -> Variable.fromObject(arg.getName(), arg, LocalDateTime.now()))
                .toList();
        currentMethodCall.set(new MethodCall(methodName, className, parameters));
        CallGraphEventManager.getInstance().onMethodCaptureEntry(currentMethodCall.get());
    }

    public void captureExit(String className, String methodName, String type, Object value) {
        MethodCall methodCall = currentMethodCall.get();
        if (methodCall != null) {
            methodCall.setEndTime(LocalDateTime.now());
            methodCall.setReturnedValue(Variable.fromObject("returnValue", value, LocalDateTime.now()));
            CallGraphEventManager.getInstance().onMethodCaptureExit(methodCall);
            currentMethodCall.remove();
        }
    }
}