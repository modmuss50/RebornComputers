package me.modmuss50.reborncomputers.computer;

import me.modmuss50.reborncomputers.util.ByteUtils;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import javax.vecmath.Point2i;
import java.util.function.Consumer;

public class Monitor implements INBTSerializable {

	private int width, height;
	private char[][] data;
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
	}

	public void print(Object object) {
		String string = object.toString();
		//TODO make this not shit, but a cursor pos is need before that can be done
		for (int i = 0; i < string.length(); i++) {
			data[i][0] = string.charAt(i);
		}
	}

	public void setData(char cha, Point2i point) {
		if (isCordValid(point)) {
			data[point.getX()][point.getY()] = cha;
		}
	}

	public char getCharAt(Point2i point){
		return data[point.getX()][point.getY()];
	}

	public boolean isCordValid(Point2i point){
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
		tagCompound.setByteArray("data", ByteUtils.write(data));
		return tagCompound;
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		NBTTagCompound tagCompound = (NBTTagCompound) nbt;
		data = (char[][]) ByteUtils.read(tagCompound.getByteArray("data"));
		width = tagCompound.getInteger("width");
		height = tagCompound.getInteger("height");
	}
}
