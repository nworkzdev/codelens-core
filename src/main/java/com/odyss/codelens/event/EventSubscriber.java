package com.odyss.codelens.event;

public interface EventSubscriber<T> {

    void update(T eventObject);
}
