package com.odyss.codelens.call;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Method Calls
 */
public class MethodCall {
    private final String id;
    private final String name;
    private final String className;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;

    //defined inside the method
    private List<Variable> variables;

    //pass from the outside of the method
    private List<Variable> arguments;

    public MethodCall(String name, String className, List<Variable> variables, List<Variable> arguments) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.className = className;
        this.startTime = LocalDateTime.now();
        this.endTime = LocalDateTime.now();
        this.variables = variables;
        this.arguments = arguments;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public List<Variable> getArguments() {
        return arguments;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
