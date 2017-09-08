package net.querz.event;

public abstract class Event {

	private boolean async;

	public Event() {
		this(false);
	}

	public Event(boolean async) {
		this.async = async;
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public boolean isAsync() {
		return async;
	}
}
