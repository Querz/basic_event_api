package net.querz.event;

import java.lang.reflect.Method;
import java.util.*;

class EventTree {

	private TreeElement root;

	EventTree() {
		root = new EventTree.TreeElement(Event.class);
	}

	void add(Class<?> eventClass, EventMethod method) {
		Class<?> clazz = eventClass;
		EventTree.TreeElement closest = getClosest(clazz);
		EventTree.TreeElement elem;
		//get closest connection in tree
		if (closest.children != null && closest.children.containsKey(eventClass)) {
			elem = closest.children.get(eventClass);
		} else {
			elem = new EventTree.TreeElement(clazz);
		}
		elem.methods.add(method);
		Collections.sort(elem.methods);

		//create branch
		while ((clazz = clazz.getSuperclass()) != Event.class && clazz != closest.eventClass) {
			EventTree.TreeElement parent = new EventTree.TreeElement(clazz);
			parent.addChild(elem);
			elem.parent = parent;
			elem = parent;
		}
		elem.parent = closest;
		closest.addChild(elem);
	}

	void remove(Class<?> eventClass, Method method) {
		EventTree.TreeElement elem = getClosest(eventClass);
		if (elem.children.get(eventClass) != null) {
			elem.children.get(eventClass).removeMethod(method);
		}
	}

	//get leaf or closest element
	private EventTree.TreeElement getClosest(Class<?> eventClass) {
		Stack<Class<?>> branch = new Stack<>();
		Class<?> current = eventClass;
		while ((current = current.getSuperclass()) != Event.class) {
			branch.push(current);
		}
		EventTree.TreeElement elem = root;
		while (!branch.isEmpty()) {
			if (elem.children != null && elem.children.containsKey(branch.peek())) {
				elem = elem.children.get(branch.pop());
			} else {
				break;
			}
		}
		return elem;
	}

	void invoke(Event event) {
		EventTree.TreeElement elem = getClosest(event.getClass());
		if (elem.children != null && elem.children.containsKey(event.getClass())) {
			elem.children.get(event.getClass()).invoke(event);
		}
	}

	@Override
	public String toString() {
		return root.toString();
	}

	class TreeElement {
		EventTree.TreeElement parent;
		Map<Class<?>, EventTree.TreeElement> children;
		Class<?> eventClass;
		List<EventMethod> methods;

		TreeElement(Class<?> eventClass) {
			this.eventClass = eventClass;
			methods = new ArrayList<>();
		}

		boolean removeMethod(Method method) {
			return methods.removeIf(r -> r.getMethod().equals(method));
		}

		void addChild(EventTree.TreeElement elem) {
			if (children == null) {
				children = new HashMap<>();
			}
			children.put(elem.eventClass, elem);
		}

		void invoke(Event event) {
			methods.forEach(method -> invokeEventMethod(method, event));
			if (parent != null) {
				parent.invoke(event);
			}
		}

		void invokeEventMethod(EventMethod method, Event event) {
			if (event.isAsync()) {
				new Thread(() -> method.invoke(event)).start();
			} else {
				method.invoke(event);
			}
		}

		@Override
		public int hashCode() {
			return eventClass.hashCode();
		}

		@Override
		public String toString() {
			return toString(0);
		}

		String toString(int depth) {
			StringBuilder d = new StringBuilder(depth);

			for (int i = 0; i < depth; i++) {
				d.append(" ");
			}

			StringBuilder out = new StringBuilder(d + "parent=" + (parent == null ? "null" : parent.eventClass.getSimpleName())
					+ "\n" + d + "eventClass=" + eventClass.getSimpleName()
					+ "\n" + d + "methods=" + Arrays.toString(methods.toArray())
					+ "\n" + d + "children:");
			if (children == null) {
				out.append("null\n");
			} else {
				out.append("\n");
				for (Map.Entry<Class<?>, EventTree.TreeElement> entry : children.entrySet()) {
					out.append(entry.getValue().toString(depth + 1));
				}
			}
			return out.toString();
		}
	}
}
