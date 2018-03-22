package me.modmuss50.reborncomputers.computer.filesystem;

import me.modmuss50.reborncomputers.util.ByteUtils;

import java.io.File;


//TODO unit tests
public class FileSystemTests {

	public static void main(String[] args) {
		System.out.println("Running file system tests");
		FileSystem fileSystem = new FileSystem(new File("test.zip"));

		System.out.println("Testing for file exists");
		System.out.println("File exists: " + fileSystem.fileExists("test"));

		System.out.println("Testing file write");
		String test = "This is a test string";
		byte[] testBytes = ByteUtils.write(test);
		fileSystem.writeFile("test", testBytes);

		System.out.println("Testing for file exists");
		System.out.println("File exists: " + fileSystem.fileExists("test"));

		System.out.println("Testing list files");
		System.out.println(fileSystem.listFiles());

		fileSystem.changeDirectory("/directory/");
		fileSystem.writeFile("test2", testBytes);

		System.out.println(fileSystem.listFiles());

	}

}
