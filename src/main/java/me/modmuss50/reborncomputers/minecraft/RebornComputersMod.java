package me.modmuss50.reborncomputers.minecraft;

import me.modmuss50.reborncomputers.computer.ComputerScriptManager;
import me.modmuss50.reborncomputers.minecraft.block.BlockComputer;
import me.modmuss50.reborncomputers.minecraft.client.GuiHandler;
import me.modmuss50.reborncomputers.minecraft.tile.TileComputer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import reborncore.RebornRegistry;

import java.io.File;

@Mod(name = "Reboorn Computers", modid = "reborncomputers")
public class RebornComputersMod {

	@Mod.Instance
	public static RebornComputersMod INSTANCE;

	public static BlockComputer blockComputer;

	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent event) {
		//TODO do something about this
		ComputerScriptManager.OS_LOCATION = new File("/Users/mark/Documents/modding/RebornComputers/src/main/resouces/assets/reborncomputers/operatingsystem");
		ComputerScriptManager.extractOS();

		blockComputer = new BlockComputer();
		RebornRegistry.registerBlock(blockComputer, new ResourceLocation("reborncomputers", "comptuer"));
		GameRegistry.registerTileEntity(TileComputer.class, "reborncomputers:computer");

		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());

	}

}
