package net.querz.event.test.dummies;

import net.querz.event.EventHandler;
import net.querz.event.Listener;
import net.querz.event.test.EventCallCollector;

public class Listener1 implements Listener {

	@EventHandler
	public void onEvent1(Event1 event) {
		System.out.println("called Event1");
		EventCallCollector.add(event.id, "Event1", event);
	}

	@EventHandler
	public void onEvent2(Event2 event) {
		System.out.println("called Event2");
		EventCallCollector.add(event.id, "Event2", event);
	}

	@EventHandler
	public void onSubEvent1(SubEvent1 event) {
		System.out.println("called SubEvent1");
		EventCallCollector.add(event.id, "SubEvent1", event);
	}

	@EventHandler
	public void onSubEvent2(SubEvent2 event) {
		System.out.println("called SubEvent2");
		EventCallCollector.add(event.id, "SubEvent2", event);
	}

	@EventHandler
	public void onSubSubEvent1(SubSubEvent1 event) {
		System.out.println("called SubSubEvent1");
		EventCallCollector.add(event.id, "SubSubEvent1", event);
	}
}
