package com.odyss.codelens.graph;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CallGraph {
    private final String id;
    private final LocalDateTime timestamp;
    private List<GraphNode> nodes;
    private List<GraphEdge> edges;

    public CallGraph() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.nodes = Collections.emptyList();
        this.edges = Collections.emptyList();
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public List<GraphEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<GraphEdge> edges) {
        this.edges = edges;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
