package logistics.company.messagedriven.consumer;


import logistics.company.messagedriven.event.Event;

public interface EventConsumer<T extends Event> {

    void consumeEvent(T event);
}
