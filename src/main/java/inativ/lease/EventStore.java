package inativ.lease;

import java.util.ArrayList;
import java.util.List;

import inativ.lease.command.contrats.events.Event;

public class EventStore {
    private static final List<Event> ALL_EVENTS = new ArrayList<>();

    public void publish(List<Event> events) {
        ALL_EVENTS.addAll(events);
    }
    
    public List<Event> findContratsEvents() {
        return ALL_EVENTS;
    }

}
