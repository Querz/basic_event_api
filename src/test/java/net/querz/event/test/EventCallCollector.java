package net.querz.event.test;

import static junit.framework.TestCase.*;
import net.querz.event.Event;

import java.util.*;

public class EventCallCollector {

	private static Map<UUID, Map<String, Event>> map = new LinkedHashMap<>();

	public static void assertEventExists(UUID id, String name, Event event) {
		assertTrue(map.containsKey(id));
		assertTrue(map.get(id).containsKey(name));
		assertTrue(map.get(id).get(name) == event);
	}

	public static void assertEventDoesNotExist(UUID id, String name) {
		if (map.containsKey(id)) {
			assertFalse(map.get(id).containsKey(name));
		}
	}

	public static void add(UUID id, String name, Event event) {
		if (map.containsKey(id)) {
			assertFalse(map.get(id).containsKey(name));
			map.get(id).put(name, event);
		} else {
			Map<String, Event> s = new HashMap<>();
			s.put(name, event);
			map.put(id, s);
		}
	}

	public static void clear() {
		map.clear();
	}
}
