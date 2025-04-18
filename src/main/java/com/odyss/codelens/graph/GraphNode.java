package com.odyss.codelens.graph;

import com.odyss.codelens.call.MethodCall;

import java.util.Objects;

public class GraphNode {
    private final String id;
    private final MethodCall methodCall;

    public GraphNode(MethodCall methodCall) {
        this.id = methodCall.getId();
        this.methodCall = methodCall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode graphNode = (GraphNode) o;
        return Objects.equals(id, graphNode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public MethodCall getMethodCall() {
        return methodCall;
    }
}
