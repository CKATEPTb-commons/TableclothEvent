package dev.ckateptb.common.tableclothevent;

public interface CancelableEvent extends Event {
    boolean isCanceled();
    void setCanceled(boolean canceled);
}
