package me.modmuss50.reborncomputers.computer;

import me.modmuss50.reborncomputers.util.ByteUtils;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import org.lwjgl.input.Keyboard;

import javax.vecmath.Point2i;
import java.util.function.Consumer;

public class Monitor implements INBTSerializable {

	private int width, height;
	private char[][] data;
	private Point2i cursor = new Point2i(0, 0);
	private boolean waitingOnInput;
	private String inputBuffer;
	//private byte[][] colorData = new byte[width][height];

	public Monitor(int width, int height) {
		this.width = width;
		this.height = height;
		data = new char[width][height];
		clear();
	}

	public Monitor(NBTTagCompound nbtTagCompound) {
		deserializeNBT(nbtTagCompound);
	}

	public void clear() {
		walkData(pair -> data[pair.getX()][pair.getY()] = ' ');
		setCursorPos(new Point2i(0, 0));
	}

	public void print(Object object) {
		String string = object.toString();
		for (int i = 0; i < string.length(); i++) {
			data[cursor.x][cursor.y] = string.charAt(i);
			cursorRight();
		}
		ComputerThreadManager.stall(250);
	}

	public void printLn(Object object) {
		print(object);
		cursorNextLine();
	}

	public void setData(char cha, Point2i point) {
		if (isCordValid(point)) {
			data[point.getX()][point.getY()] = cha;
		}
	}

	public void setCursorPos(Point2i pos) {
		cursor = pos;
	}

	public Point2i getCursorPos() {
		return cursor;
	}

	private void cursorRight() {
		cursor.x++;
		if (cursor.x == width) {
			cursor.x = 0;
			cursorNextLine();
		}
	}

	private void cursorNextLine() {
		cursor.y++;
		if (cursor.y >= height) {
			cursor.y = height;
			scrollMonitor();
		}
		cursor.x = 0;
	}

	private void scrollMonitor() {
		for (int i = height; i > 1; i -= 1) {
			data[i-1] = data[i];
		}
		data[height] = new char[width];
	}

	public char getCharAt(Point2i point) {
		return data[point.getX()][point.getY()];
	}

	public boolean isCordValid(Point2i point) {
		return isCordValid(point.getX(), point.getY());
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

	public void keyTyped(char cha, int keyCode){
		if(waitingOnInput){
			if(keyCode == Keyboard.KEY_RETURN){
				waitingOnInput = false;
				return;
			} else {
				inputBuffer = inputBuffer + cha;
			}
		}
		print(cha);
	}

	public String waitForLine(){
		waitingOnInput = true;
		while(waitingOnInput){
			//wait till input finished
		}
		String buffer = inputBuffer;
		inputBuffer = "";
		return buffer;
	}

	public void walkData(Consumer<Point2i> consumer) {
		Point2i point = new Point2i();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				point.set(x, y);
				consumer.accept(point);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void printData() {
		walkData(pair -> {
			System.out.print(data[pair.getX()][pair.getY()]);
			if (pair.getX() == width) {
				System.out.println();
			}
		});
	}

	@Override
	public NBTBase serializeNBT() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setInteger("width", width);
		tagCompound.setInteger("height", height);
		tagCompound.setInteger("cursorx", cursor.x);
		tagCompound.setInteger("cursory", cursor.y);
		tagCompound.setByteArray("data", ByteUtils.write(data));
		return tagCompound;
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		NBTTagCompound tagCompound = (NBTTagCompound) nbt;
		data = (char[][]) ByteUtils.read(tagCompound.getByteArray("data"));
		width = tagCompound.getInteger("width");
		height = tagCompound.getInteger("height");
		cursor.x = tagCompound.getInteger("cursorx");
		cursor.y = tagCompound.getInteger("cursory");
	}
}
