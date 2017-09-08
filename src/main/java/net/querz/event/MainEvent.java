package net.querz.event;

public class MainEvent implements Listener {

	public static void main(String[] args) throws InterruptedException {
		MainEvent main = new MainEvent();

		EventManager m = new EventManager();
		m.registerEvents(main);

		m.call(new Event1(1337, "testing"));


		System.out.println(m);

		System.out.println("test");
	}

	@EventHandler
	public void onTest(Event1 event) {
		System.out.println("onTest " + event.t1 + " " + event.t2);
	}

	@EventHandler
	public void onTest2(SubSubEvent1 event) {
		System.out.println("onTest2 " + event.t1 + " " + event.t2);
	}

	@EventHandler
	public void onTest3(SubEvent1 event) {
		System.out.println("onTest3 " + event.t1 + " " + event.t2);
	}

	@EventHandler(priority = 5)
	public void onTest4(Event1 event) {
		System.out.println("onTest4 " + event.t1 + " " + event.t2);
	}
}
