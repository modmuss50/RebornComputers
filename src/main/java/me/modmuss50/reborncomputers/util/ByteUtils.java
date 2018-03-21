package me.modmuss50.reborncomputers.util;

import java.io.*;

public class ByteUtils {

	public static byte[] write(Object object){
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(object);
			out.flush();
			byte[] bytes = bos.toByteArray();
			bos.close();
			return bytes;
		} catch (IOException e){
			throw new RuntimeException("Failed to write object", e);
		}
	}

	public static Object read(byte[] bytes){
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInput in = new ObjectInputStream(bis);
			Object obj = in.readObject();
			in.close();
			return obj;
		} catch (IOException | ClassNotFoundException e){
			throw new RuntimeException("Failed to read object", e);
		}
	}
}
