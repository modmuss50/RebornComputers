package me.modmuss50.reborncomputers.computer;

import me.modmuss50.reborncomputers.util.ByteUtils;
import me.modmuss50.reborncomputers.util.CharPos;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Arrays;
import java.util.function.Consumer;

public class Monitor implements INBTSerializable {

	private int width, height;
	private char[][] data;
	private CharPos cursor = new CharPos(0, 0);
	private volatile boolean waitingOnInput;
	private final StringBuilder inputBuffer = new StringBuilder();
	//private byte[][] colorData = new byte[width][height];

	public Monitor(int width, int height) {
		this.width = width;
		this.height = height;
		data = new char[height][width];
		clear();
	}

	public Monitor(NBTTagCompound nbtTagCompound) {
		deserializeNBT(nbtTagCompound);
	}

	public void clear() {
		walkData(pair -> data[pair.row][pair.column] = ' ');
		setCursorPos(new CharPos(0, 0));
	}

	public void print(Object object) {
		String string = object.toString();
		for (int i = 0; i < string.length(); i++) {
			data[cursor.row][cursor.column] = string.charAt(i);
			cursorRight();
		}
		//ComputerThreadManager.stall(250);
	}

	public void printLn(Object object) {
		print(object);
		cursorNextLine();
	}

	public void setData(char cha, CharPos point) {
		if (isCordValid(point)) {
			data[point.row][point.column] = cha;
		}
	}

	public void setCursorPos(CharPos pos) {
		cursor = pos;
	}

	public CharPos getCursorPos() {
		return cursor;
	}

	private void cursorRight() {
		cursor.column++;
		if (cursor.column == width) {
			cursor.column = 0;
			cursorNextLine();
		}
	}

	private void cursorNextLine() {
		cursor.row++;
		if (cursor.row >= height) {
			cursor.row = height - 1;
			scrollMonitor();
		}
		cursor.column = 0;
	}

	private void scrollMonitor() {
		try{
			for (int row = 0; row < height -1; row++) {
				char[] chars = data[row + 1];
				data[row] = Arrays.copyOf(chars, chars.length);
			}
			data[height -1] = new char[width];
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public char getCharAt(CharPos point) {
		return data[point.row][point.column];
	}

	public boolean isCordValid(CharPos point) {
		return isCordValid(point.row, point.column);
	}

	public boolean isCordValid(int row, int column) {
		if (row < 0 || column < 0) {
			return false;
		}
		if (column > width | row > width) {
			return false;
		}
		return true;
	}

	public void keyTyped(char cha, int keyCode) {
		if (waitingOnInput) {
			if (keyCode == 28) {
				waitingOnInput = false;
				return;
			} else {
				if (ChatAllowedCharacters.isAllowedCharacter(cha)) {
					inputBuffer.append(cha);
				}
			}

		}
		print(cha);
	}

	public String waitForLine() {
		waitingOnInput = true;
		while (waitingOnInput) {
				//wait till input finished
		}
		String buffer = inputBuffer.toString();
		inputBuffer.setLength(0);
		waitingOnInput = false;
		return buffer;
	}

	public void walkData(Consumer<CharPos> consumer) {
		CharPos charPos = new CharPos();
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				charPos.set(row, column);
				consumer.accept(charPos);
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
			System.out.print(data[pair.row][pair.column]);
			if (pair.column == width) {
				System.out.println();
			}
		});
	}

	@Override
	public NBTBase serializeNBT() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setInteger("width", width);
		tagCompound.setInteger("height", height);
		tagCompound.setInteger("cursorRow", cursor.row);
		tagCompound.setInteger("cursorColumn", cursor.column);
		tagCompound.setByteArray("data", ByteUtils.write(data));
		return tagCompound;
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		NBTTagCompound tagCompound = (NBTTagCompound) nbt;
		data = (char[][]) ByteUtils.read(tagCompound.getByteArray("data"));
		width = tagCompound.getInteger("width");
		height = tagCompound.getInteger("height");
		cursor.row = tagCompound.getInteger("cursorRow");
		cursor.column = tagCompound.getInteger("cursorColumn");
	}
}
