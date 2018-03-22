package me.modmuss50.reborncomputers.computer.filesystem;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.lingala.zip4j.util.Zip4jUtil;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileSystem {

	public FileSystem(File file){
		try {
			this.zipFile = new ZipFile(file);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	ZipFile zipFile;
	String currentDir = "";

	public byte[] readFile(String filename){
		try {
			InputStream inputStream =  zipFile.getInputStream(getFile(filename));
			byte[] bytes = IOUtils.toByteArray(inputStream);
			inputStream.close();
			return bytes;
		} catch (ZipException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeFile(String fileName, byte[] bytes){
		if(fileExists(fileName)){
			listFiles().stream()
				.filter(s -> getFile(fileName) != null && s.equals(getFile(fileName).getFileName()))
				.forEach(this::deleteFile);
		}
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
			zipFile.addStream(inputStream, getParameters(fileName));
			inputStream.close();
		} catch (ZipException | IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteFile(String fileName){
		try {
			zipFile.removeFile(fileName);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	public boolean fileExists(String fileName){
		return getFile(fileName) != null;
	}

	public List<String> listFiles(){
		if(!zipHasData()){
			return Collections.emptyList();
		}
		try {
			List<FileHeader> files = zipFile.getFileHeaders();
			return files.stream()
				.map(FileHeader::getFileName)
				.filter(s -> s.startsWith(currentDir))
				.filter(s -> !currentDir.isEmpty() || !s.startsWith("/"))
				.collect(Collectors.toList());
		} catch (ZipException e) {
			e.printStackTrace();
		}
		//TODO
		return Collections.emptyList();
	}

	public boolean changeDirectory(String newDir){
		//TODO make this not shite
		this.currentDir = newDir;
		return true;
	}

	private ZipParameters getParameters(String name){
		ZipParameters parameters = new ZipParameters();
		parameters.setFileNameInZip(currentDir + name);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setSourceExternalStream(true);
		return parameters;
	}

	private String getFileName(String name){
		return currentDir +  name;
	}

	private FileHeader getFile(String name){
		if(!zipHasData()){
			return null;
		}
		try {
			return zipFile.getFileHeader(getFileName(name));
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean zipHasData(){
		try {
			return Zip4jUtil.checkFileExists(zipFile.getFile());
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return false;
	}
}
