package me.modmuss50.reborncomputers;

import me.modmuss50.reborncomputers.computer.Computer;
import me.modmuss50.reborncomputers.computer.ComputerScriptManager;

import java.io.File;

public class RebornComputersMain {

	public static void main(String[] args) {
		ComputerScriptManager.OS_LOCATION = new File("src/main/resouces/assets/reborncomputers/operatingsystem");
		ComputerScriptManager.extractOS();

		Computer computer = new Computer();
		computer.start();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		computer.getMontior(0).printData();

//		Computer computer2 = new Computer();
//		System.out.println("Starting 2");
//		computer2.start();
	}

}
