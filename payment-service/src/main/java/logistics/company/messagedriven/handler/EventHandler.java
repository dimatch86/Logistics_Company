package logistics.company.messagedriven.handler;


import logistics.company.messagedriven.event.Event;

public interface EventHandler<T extends Event, R extends Event> {

    R handleEvent(T event);
}
