package net.querz.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventMethod implements Comparable<EventMethod> {
	private Method method;
	private Listener listener;
	private int priority;

	EventMethod(Listener listener, Method method) {
		this.listener = listener;
		this.method = method;
		priority = method.getAnnotation(EventHandler.class).priority();
	}

	public void invoke(Event event) {
		try {
			method.invoke(listener, event);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public Listener getListener() {
		return listener;
	}

	public Method getMethod() {
		return method;
	}

	@Override
	public int compareTo(EventMethod o) {
		return priority > o.priority ? 1 : priority < o.priority ? -1 : 0;
	}
}
