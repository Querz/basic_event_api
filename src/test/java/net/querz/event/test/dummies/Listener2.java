package net.querz.event.test.dummies;

import static junit.framework.TestCase.*;
import net.querz.event.EventHandler;
import net.querz.event.Listener;
import net.querz.event.test.EventCallCollector;

public class Listener2 implements Listener {

	@EventHandler
	public void onAsyncEvent1(AsyncEvent1 event) {
		event.setThread();
		EventCallCollector.add(event.id, "AsyncEvent1", event);
	}
}
