package com.odyss.codelens.event;

import com.odyss.codelens.call.MethodCall;
import com.odyss.codelens.graph.CallGraph;
import com.odyss.codelens.graph.GraphNode;


public class CallGraphEventManager extends EventManager<CallGraph> {

    private final CallGraph callGraph;

    private static class Holder {
        static final CallGraphEventManager INSTANCE = new CallGraphEventManager();
    }

    public static CallGraphEventManager getInstance() {
        return Holder.INSTANCE;
    }

    public CallGraphEventManager() {
        callGraph = new CallGraph();
    }

    public void onMethodCaptureEntry(MethodCall methodCall) {
        callGraph.getNodes().add(new GraphNode(methodCall));

        updateSubscribers(callGraph);
    }

    public void onMethodCaptureExit(MethodCall methodCall) {
        callGraph.getNodes().stream()
                .filter(node -> node.getMethodCall().getId().equals(methodCall.getId()))
                .findFirst()
                .ifPresent(n -> {
                    n.getMethodCall().setEndTime(methodCall.getEndTime());
                    n.getMethodCall().setReturnedValue(methodCall.getReturnedValue());
                });

        updateSubscribers(callGraph);
    }
}
