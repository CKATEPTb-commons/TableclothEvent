package dev.ckateptb.common.tableclothevent;

public interface EventHandler<E extends Event> {
    void handle(E event);
}
