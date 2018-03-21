package me.modmuss50.reborncomputers;

import me.modmuss50.reborncomputers.computer.ComputerScriptManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(name = "Reboorn Computers", modid = "reborncomputers")
public class RebornComputersMod {

	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent event) {
		ComputerScriptManager.OS_LOCATION = new File(event.getModConfigurationDirectory().getParent(), "reborncomputers");
		ComputerScriptManager.extractOS();
	}

}
