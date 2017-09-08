package net.querz.event;

public interface Cancellable {
	void setCancelled(boolean cancel);
	boolean isCancelled();
}
