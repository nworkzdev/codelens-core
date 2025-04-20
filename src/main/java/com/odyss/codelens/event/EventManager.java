package com.odyss.codelens.event;

import java.util.ArrayList;
import java.util.List;

public class EventManager<T> {

    protected List<EventSubscriber<T>> subscribers;

    public EventManager() {
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(EventSubscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(EventSubscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }

    protected void updateSubscribers(T eventObject) {
        subscribers.forEach(subscriber -> subscriber.update(eventObject));
    }
}
