package me.modmuss50.reborncomputers.computer;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Consumer;

public class Monitor {

	private int width, height;
	private char[][] data = new char[width][height];
	private byte[][] colorData = new byte[width][height];

	public void clear() {
		walkData(pair -> data[pair.getLeft()][pair.getRight()] = ' ');
	}

	public void print(String string) {
		//TODO make this not shit, but a cursor pos is need before that can be done
		for (int i = 0; i < string.length(); i++) {
			data[i][0] = string.charAt(i);
		}
	}

	public void setData(char cha, int x, int y) {
		if (isCordValid(x, y)) {
			data[x][y] = cha;
		}
	}

	public boolean isCordValid(int x, int y) {
		if (x < 0 || y < 0) {
			return false;
		}
		if (x > width | y > width) {
			return false;
		}
		return true;
	}

	public void walkData(Consumer<Pair<Integer, Integer>> consumer) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				consumer.accept(Pair.of(x, y));
			}
		}
	}

	public void printData() {
		walkData(pair -> {
			System.out.print(data[pair.getLeft()][pair.getRight()]);
			if (pair.getLeft() == width) {
				System.out.println();
			}
		});
	}

}
