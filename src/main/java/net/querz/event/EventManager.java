package net.querz.event;

import java.lang.reflect.Method;
import java.util.*;

public class EventManager {

	private EventTree events;

	public EventManager() {
		events = new EventTree();
	}

	public void registerEvents(Listener listener) {
		for (Method method : listener.getClass().getMethods()) {
			if (method.isAnnotationPresent(EventHandler.class)
					&& method.getParameterCount() == 1
					&& Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
				events.add(method.getParameterTypes()[0], new EventMethod(listener, method));
			}
		}
	}

	public void unregisterEvents(Listener listener) {
		for (Method method : listener.getClass().getMethods()) {
			if (method.isAnnotationPresent(EventHandler.class)
					&& method.getParameterCount() == 1
					&& Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
				events.remove(method.getParameterTypes()[0], method);
			}
		}
	}

	public void call(Event event) {
		events.invoke(event);
	}

	@Override
	public String toString() {
		return events.toString();
	}
}
