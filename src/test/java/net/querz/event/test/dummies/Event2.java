package net.querz.event.test.dummies;

import net.querz.event.Event;

import java.util.UUID;

public class Event2 extends Event {
	public UUID id;
	public String msg;

	public Event2(UUID id, String msg) {
		this.id = id;
		this.msg = msg;
	}

}
