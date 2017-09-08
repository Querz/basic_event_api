package net.querz.event.test.dummies;

import net.querz.event.Event;

import java.util.UUID;

public class AsyncEvent1 extends Event {

	private Thread thread;
	public UUID id;

	public AsyncEvent1(UUID id, boolean async) {
		super(async);
		this.id = id;
	}

	public Thread getThread() {
		return thread;
	}

	void setThread() {
		thread = Thread.currentThread();
	}

	public void join() {
		try {
			if (isAsync()) {
				while (thread == null);
				thread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
