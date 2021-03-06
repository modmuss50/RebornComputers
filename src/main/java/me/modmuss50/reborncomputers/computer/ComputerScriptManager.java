package me.modmuss50.reborncomputers.computer;

import jdk.nashorn.api.scripting.ClassFilter;
import me.modmuss50.reborncomputers.RebornComputers;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ComputerScriptManager {

	public static final String INIT_SCRIPT = "init.js";
	public static File OS_LOCATION;

	private static Map<String, ComputerScriptManager> scriptManagerMap = new HashMap<>();

	public static ComputerScriptManager getManagerForComputer(Computer computer) {
		if (scriptManagerMap.containsKey(computer.getReference())) {
			return scriptManagerMap.get(computer.getReference());
		}
		ComputerScriptManager scriptManager = new ComputerScriptManager(computer.getReference());
		scriptManagerMap.put(computer.getReference(), scriptManager);
		return scriptManager;
	}

	/**
	 * This method is used to extract the computer OS out of the resources and into the current working directory
	 */
	public static void extractOS() {
		if (!OS_LOCATION.exists()) {
			OS_LOCATION.mkdir();
		}
		File initFile = new File(OS_LOCATION, INIT_SCRIPT);
		if (!initFile.exists()) {
			RebornComputers.LOGGER.error("Failed to find " + INIT_SCRIPT + " in " + initFile.getAbsolutePath());
			//TODO extract all of the os files
		}

	}

	Context cx = Context.enter();
	Scriptable scope = cx.initStandardObjects();
	Script initScript;

	private final String reference;

	public ComputerScriptManager(String reference) {
		this.reference = reference;
	}

	public void bootOperatingSystem() {
		try {
			Map<String, Script> scriptMap = new HashMap<>();
			Files.walk(OS_LOCATION.toPath()).forEach(path -> {
				try {
					File file = path.toFile();
					if(!file.isDirectory() && file.exists()){
						scriptMap.put(file.getName(), compileFile(file));
					}
				} catch (IOException e) {
					RebornComputers.LOGGER.error("Failed to eval " + path);
					e.printStackTrace();
					return;
				}
			});
			initScript = scriptMap.get("init.js");
			scriptMap.get("require.js").exec(cx, scope);
		} catch (IOException e) {
			RebornComputers.LOGGER.error("Failed to pre load OS");
			e.printStackTrace();
			return;
		}

		Optional<String> computerRef = ComputerThreadManager.getComputerOnCurrentThread();
		if (computerRef.isPresent()) {
			try {
				initScript.exec(cx, scope);
			} catch (Exception e) {
				RebornComputers.LOGGER.error("An expection occured when running OS on " + computerRef.get());
				e.printStackTrace();
			}
		} else {
			RebornComputers.LOGGER.error("OS can only be started on the main thread!");
		}
	}

	private Script compileFile(File file) throws IOException {
		return cx.compileReader(new FileReader(file), file.getName(), 0, null);
	}

	private static class Filter implements ClassFilter {

		//This is used to whitelist java classes that the computers can use. In practice theses classes should only be RC classes, and possibly java's. But in no case minecraft's or any other mods.
		@Override
		public boolean exposeToScripts(String s) {
			return s.startsWith("me.modmuss50.reborncomputers.computer.bindings.");
		}
	}

}
