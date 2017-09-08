package net.querz.event.test.dummies;

import net.querz.event.Event;

import java.util.UUID;

public class Event1 extends Event {

	public UUID id;
	public String msg;

	public Event1(UUID id, String msg) {
		this.id = id;
		this.msg = msg;
	}
}
