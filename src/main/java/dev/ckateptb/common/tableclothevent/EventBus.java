package dev.ckateptb.common.tableclothevent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventBus {
    public static final EventBus GLOBAL = new EventBus();
    private final Map<Class<? extends Event>, Set<EventHandlerWrapper<? extends Event>>> eventHandlers = new HashMap<>();

    public <E extends Event> void dispatchEvent(E event) {
        Class<E> eventClass = (Class<E>) event.getClass();
        if (eventHandlers.containsKey(eventClass)) {
            Set<EventHandlerWrapper<? extends Event>> eventHandlerWrappers = eventHandlers.get(eventClass);
            eventHandlerWrappers.stream()
                    .map(eventHandlerWrapper -> (EventHandlerWrapper<E>) eventHandlerWrapper)
                    .sorted((o1, o2) -> {
                        EventPriority first = o1.getPriority();
                        EventPriority second = o2.getPriority();
                        return first.compareTo(second);
                    }).forEachOrdered(wrapper -> {
                        if (!(event instanceof CancelableEvent cancelableEvent)
                                || !cancelableEvent.isCanceled()
                                || wrapper.isIgnoreCanceled()) {
                            wrapper.handle(event);
                            System.out.println(wrapper.getPriority().name());
                        }
                    });
        }
    }

    public <E extends Event, H extends EventHandler<E>> EventHandlerWrapper<E> registerEventHandler(Class<E> event, H handler) {
        return registerEventHandler(event, handler, EventPriority.NORMAL);
    }

    public <E extends Event, H extends EventHandler<E>> EventHandlerWrapper<E> registerEventHandler(Class<E> event, H handler, EventPriority priority) {
        EventBus finalEventBus = this;
        EventHandlerWrapper<E> registeredEventHandlerWrapper = new EventHandlerWrapper<>() {
            private boolean ignoreCanceled;

            @Override
            public void handle(E event) {
                handler.handle(event);
            }

            @Override
            public EventBus getEventBus() {
                return finalEventBus;
            }

            @Override
            public EventPriority getPriority() {
                return priority;
            }

            @Override
            public void unregister() {
                if (eventHandlers.containsKey(event)) {
                    eventHandlers.get(event).remove(this);
                }
            }

            @Override
            public boolean isIgnoreCanceled() {
                return this.ignoreCanceled;
            }

            @Override
            public void setIgnoreCanceled(boolean ignoreCanceled) {
                this.ignoreCanceled = ignoreCanceled;
            }
        };
        eventHandlers.computeIfAbsent(event, key -> new HashSet<>()).add(registeredEventHandlerWrapper);
        return registeredEventHandlerWrapper;
    }
}
