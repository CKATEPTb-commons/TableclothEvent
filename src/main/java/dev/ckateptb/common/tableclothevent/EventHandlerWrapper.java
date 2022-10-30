package dev.ckateptb.common.tableclothevent;

public interface EventHandlerWrapper<E extends Event> extends EventHandler<E> {
    EventBus getEventBus();

    EventPriority getPriority();

    void unregister();

    boolean isIgnoreCanceled();

    void setIgnoreCanceled(boolean ignoreCanceled);
}
