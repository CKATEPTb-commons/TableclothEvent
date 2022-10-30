package dev.ckateptb.common.tableclothevent;

public enum EventPriority {
    LOWEST, // The handler with this priority will be the FIRST to know when the event fires.
    LOW,
    NORMAL,
    HIGH,
    HIGHEST, // The handler with this priority will be the LAST to know when the event fires.
}
