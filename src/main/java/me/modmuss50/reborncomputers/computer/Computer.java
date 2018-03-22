package me.modmuss50.reborncomputers.computer;

import me.modmuss50.reborncomputers.RebornComputers;

import java.util.UUID;

public class Computer {

	private final UUID reference;
	private boolean running = false;
	//This in done like this to allow for more monitors in the future, but now assume there is one
	private Monitor[] monitors = new Monitor[] { new Monitor(64, 32) };

	public Computer(UUID reference) {
		this.reference = reference;
		ComputerManager.computerMap.put(getReference(), this);
	}

	public Computer(String reference) {
		this(UUID.fromString(reference));
	}

	public Computer() {
		this(UUID.randomUUID());
	}

	public int getMonitorCount() {
		return 1;
	}

	public Monitor getMontior(int id) {
		return monitors[0]; //TODO allow more than 1 monitor in the future
	}

	//============================================================================

	/**
	 * Used to start the computer, this creates a new thread for it to run on.
	 */
	public void start() {
		ComputerThreadManager.startComputerThread(this);
	}

	public String getReference() {
		return reference.toString();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	private void run() {
		if (!isRunning()) {
			RebornComputers.LOGGER.error("Computer is not set running!");
			return;
		}
		if (!ComputerThreadManager.getComputerOnCurrentThread().isPresent()) {
			RebornComputers.LOGGER.error("Computer isnt on the correct thread!");
			return;
		}
		ComputerScriptManager.getManagerForComputer(this).bootOperatingSystem();
	}

	public Runnable getRunnable() {
		return this::run;
	}

	@Override
	public String toString() {
		return "Computer{" + "reference='" + reference + '\'' + ", running=" + running + '}';
	}
}
