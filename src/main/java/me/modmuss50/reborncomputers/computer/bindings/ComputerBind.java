package me.modmuss50.reborncomputers.computer.bindings;

import me.modmuss50.reborncomputers.computer.Computer;
import me.modmuss50.reborncomputers.computer.ComputerManager;
import me.modmuss50.reborncomputers.computer.ComputerThreadManager;

import javax.vecmath.Point2i;
import java.util.Optional;

public class ComputerBind {

	public static void print(Object object){
		System.out.println(object);
		getCurrentComputer().getMontior(0).print(object);
	}

	public static void clear(){
		getCurrentComputer().getMontior(0).clear();
	}

	public static void setPoint(char charr, int x, int y){
		getCurrentComputer().getMontior(0).setData(charr, new Point2i(x, y));
	}

	public static int getHeight(){
		return getCurrentComputer().getMontior(0).getHeight();
	}

	public static int getWidth(){
		return getCurrentComputer().getMontior(0).getWidth();
	}

	private static Computer getCurrentComputer(){
		System.out.println(ComputerManager.computerMap);
		Optional<String> ref = ComputerThreadManager.getComputerOnCurrentThread();
		if(ref.isPresent() && ComputerManager.computerMap.containsKey(ref.get())){
			return ComputerManager.computerMap.get(ref.get());
		}
		throw new RuntimeException("Failed to get computer");
	}

}
