package org.jbpm.console.util;

public class ShutdownHook extends Thread {
	private static ShutdownHook instance = null;

	protected ShutdownHook() {
		;
	}

	public static ShutdownHook getInstance() {
		if (instance == null) {
			instance = new ShutdownHook();
		}
		return instance;
	}

	public void run() {
		System.out.println("Calling shutdown routine.");
	}

}
