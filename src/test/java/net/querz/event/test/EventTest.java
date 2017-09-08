package net.querz.event.test;

import junit.framework.TestCase;
import net.querz.event.EventManager;
import net.querz.event.test.dummies.*;

import java.util.UUID;

public class EventTest extends TestCase {

	public void testEventManager() {
		EventManager m = new EventManager();
		Listener1 l1 = new Listener1();
		m.registerEvents(l1);

		Event1 e1 = new Event1(UUID.randomUUID(), "Event1");
		m.call(e1);
		EventCallCollector.assertEventExists(e1.id, "Event1", e1);

		SubSubEvent1 sse1 = new SubSubEvent1(UUID.randomUUID(), "SubSubEvent1");
		m.call(sse1);
		EventCallCollector.assertEventExists(sse1.id, "SubSubEvent1", sse1);
		EventCallCollector.assertEventExists(sse1.id, "SubEvent1", sse1);
		EventCallCollector.assertEventExists(sse1.id, "Event1", sse1);
		EventCallCollector.assertEventDoesNotExist(sse1.id, "Event2");

		Event2 e2 = new Event2(UUID.randomUUID(), "Event2");
		m.call(e2);
		EventCallCollector.assertEventExists(e2.id, "Event2", e2);
		EventCallCollector.assertEventDoesNotExist(e2.id, "Event1");

		m.unregisterEvents(l1);
		EventCallCollector.clear();

		m.call(e1);
		EventCallCollector.assertEventDoesNotExist(e1.id, "Event1");

		m.call(sse1);
		EventCallCollector.assertEventDoesNotExist(sse1.id, "SubSubEvent1");
		EventCallCollector.assertEventDoesNotExist(sse1.id, "SubEvent1");
		EventCallCollector.assertEventDoesNotExist(sse1.id, "Event1");

		EventCallCollector.clear();

		Listener2 l2 = new Listener2();
		m.registerEvents(l2);

		AsyncEvent1 ae1 = new AsyncEvent1(UUID.randomUUID(), true);
		m.call(ae1);
		ae1.join();
		assertNotSame(Thread.currentThread(), ae1.getThread());
		EventCallCollector.assertEventExists(ae1.id, "AsyncEvent1", ae1);

		AsyncEvent1 ae1Sync = new AsyncEvent1(UUID.randomUUID(), false);
		m.call(ae1Sync);
		ae1Sync.join();
		assertEquals(Thread.currentThread(), ae1Sync.getThread());
	}
}
