package com.odyss.codelens.graph;

import java.util.Objects;

public class GraphEdge {
    private final String id;
    private final String sourceNodeId;
    private final String targetNodeId;

    public GraphEdge(String sourceNodeId, String targetNodeId) {
        Objects.requireNonNull(sourceNodeId, "Source node ID cannot be null");
        Objects.requireNonNull(targetNodeId, "Target node ID cannot be null");
        this.id = sourceNodeId + "->" + targetNodeId;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
    }

    public String getId() {
        return id;
    }

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public String getTargetNodeId() {
        return targetNodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphEdge graphEdge = (GraphEdge) o;
        return Objects.equals(sourceNodeId, graphEdge.sourceNodeId) &&
                Objects.equals(targetNodeId, graphEdge.targetNodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceNodeId, targetNodeId, id);
    }
}
