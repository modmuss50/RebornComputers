package me.modmuss50.reborncomputers.computer;

import me.modmuss50.reborncomputers.RebornComputers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ComputerThreadManager {

	private static Map<String, Thread> threadMap = new HashMap<>();
	private static final String THREAD_PREFIX = "RebornComputers-";

	public static void startComputerThread(Computer computer) {

		if (threadMap.containsKey(computer.getReference())) {
			destoryThread(computer);
		}
		Thread thread = new Thread(computer.getRunnable());
		thread.setName(THREAD_PREFIX + computer.getReference());
		threadMap.put(computer.getReference(), thread);
		computer.setRunning(true);
		thread.start();
		//RebornComputers.LOGGER.info("Started computer " + computer);
	}

	public static void destoryThread(Computer computer) {
		Thread thread = threadMap.get(computer.getReference());
		computer.setRunning(false);
		thread.stop(); //TODO use interput?
		RebornComputers.LOGGER.info("Stopped computer " + computer);
	}

	public static Optional<String> getComputerOnCurrentThread() {
		return getComputerOnThread(Thread.currentThread());
	}

	public static Optional<String> getComputerOnThread(Thread thread) {
		if (!thread.getName().startsWith(THREAD_PREFIX)) {
			return Optional.empty();
		}
		String ref = thread.getName().replace(THREAD_PREFIX, "");
		if (threadMap.containsKey(ref)) {
			return Optional.of(ref);
		}
		return Optional.empty();
	}

}
