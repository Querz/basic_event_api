# basic_event_api
A simple event api supporting inheritance
---
### Creating an Event
```java
public class ExampleEvent extends Event {
  private String msg;
  
  public ExampleEvent(String msg) {
    this.msg = msg;
  }
  
  public String getMessage() {
    return msg;
  }
}
```
---
### Creating a cancellable Event
```java
public class ExampleEvent extends Event implements Cancellable {
  private boolean cancelled;
  private String msg;
  
  public ExampleEvent(String msg) {
    this.msg = msg;
  }
  
  public String getMessage() {
    return msg;
  }
  
  @Override
  public boolean isCancelled() {
    return cancelled;
  }
  
  @Override
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }
}
```
---
### Listening for an Event
```java
public class ExampleListener implements Listener {
  @EventHandler
  public void onEvent(ExampleEvent event) {
    System.out.println(event.getMessage());
    
    //if ExampleEvent implements Cancellable, it can be cancelled:
    event.setCancelled(true);
  }
}
```
---
### Registering a Listener
```java
EventManager eventManager = new EventManager();
eventManager.registerEvents(new ExampleListener());
```
---
### Calling an Event
```java
ExampleEvent event = new ExampleEvent("Hello World!");
eventManager.call(event);

//if ExampleEvent implements Cancellable, the cancel status can be checked here:
if (event.isCancelled()) {
  //do something
} else {
  //do something else
}
```
