package dev.ckateptb.common.tableclothevent;

public abstract class AbstractCancelableEvent implements CancelableEvent {
    private boolean canceled;

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
