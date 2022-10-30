<p align="center">
<h3 align="center">TableclothEvent</h3>

------

<p align="center">
Create, dispatch and handle events according to priority. Easy to use thanks to lambda function and generics.
</p>

<p align="center">
<img alt="License" src="https://img.shields.io/github/license/CKATEPTb-commons/TableclothEvent">
<a href="#Download"><img alt="Sonatype Nexus (Snapshots)" src="https://img.shields.io/nexus/s/dev.ckateptb.common/TableclothEvent?label=repo&server=https://repo.animecraft.fun/"></a>
<img alt="Publish" src="https://img.shields.io/github/workflow/status/CKATEPTb-commons/TableclothEvent/Publish/production">
<a href="https://docs.gradle.org/7.5/release-notes.html"><img src="https://img.shields.io/badge/Gradle-7.5-brightgreen.svg?colorB=469C00&logo=gradle"></a>
<a href="https://discord.gg/P7FaqjcATp" target="_blank"><img alt="Discord" src="https://img.shields.io/discord/925686623222505482?label=discord"></a>
</p>

------

# Versioning

We use [Semantic Versioning 2.0.0](https://semver.org/spec/v2.0.0.html) to manage our releases.

# Features

- [X] Lightweight
- [X] Easy to use
- [X] Create custom events
- [X] Cancelable events
- [X] Create lambda-based event eventHandlerWrappers
- [X] Documented


# Download

Download from our repository or depend via Gradle:

```kotlin
repositories {
    maven("https://repo.animecraft.fun/repository/maven-snapshots/")
}

dependencies {
    implementation("dev.ckateptb.common:TableclothEvent:<version>")
}
```

# How To

* Import the dependency [as shown above](#Download)
* Create own EventBus instance or use global instance
```java
import dev.ckateptb.common.tableclothevent.EventBus;

public class EventBusInstanceExample {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus(); // For scoped instance
        // or
        EventBus eventBus = EventBus.GLOBAL; // For global instance
    }
}
```

* Create custom event
```java
import dev.ckateptb.common.tableclothevent.AbstractCancelableEvent;

// If you plan on making your event impossible to cancel use implement Event instead of extends AbstractCancelableEvent
public class CustomEventExample extends AbstractCancelableEvent {
    private final String unmodifiableField;
    private boolean modifiableField;

    public CustomEventExample(String unmodifiableField, boolean modifiableField) {
        this.unmodifiableField = unmodifiableField;
        this.modifiableField = modifiableField;
    }

    public String getUnmodifiableField() {
        return unmodifiableField;
    }

    public boolean isModifiableField() {
        return modifiableField;
    }

    public void setModifiableField(boolean modifiableField) {
        this.modifiableField = modifiableField;
    }
}
```
* Register a handler for the event you want
```java
import dev.ckateptb.common.tableclothevent.EventBus;
import dev.ckateptb.common.tableclothevent.EventHandlerWrapper;
import dev.ckateptb.common.tableclothevent.EventPriority;

public class RegisterEventHandlerExample {
    public static void main(String[] args) {
        // Registering a handler in the LOWEST priority (look EventPriority for more info)
        EventHandlerWrapper<CustomEventExample> handler = EventBus.GLOBAL.registerEventHandler(CustomEventExample.class, event -> {
            // ... you code here
            // Example:
            if (event.isModifiableField()) {
                event.setModifiableField(false);
                System.out.println(event.getUnmodifiableField());
            } else {
                // You can cancel event for subsequent handlers
                event.setCanceled(true);
            }
        }, EventPriority.LOWEST);
        // If you wish to handle events even if they are canceled use next line
        handler.setIgnoreCanceled(true);
        // If you wish to unregister event handler use next lint 
        handler.unregister();
    }
}
```