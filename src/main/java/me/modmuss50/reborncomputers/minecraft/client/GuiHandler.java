package me.modmuss50.reborncomputers.minecraft.client;

import me.modmuss50.reborncomputers.minecraft.client.gui.ContainerComputer;
import me.modmuss50.reborncomputers.minecraft.client.gui.GuiComputer;
import me.modmuss50.reborncomputers.minecraft.tile.TileComputer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

	public static final int COMPUTER = 0;

	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == COMPUTER){
			return new ContainerComputer((TileComputer) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == COMPUTER){
			return new GuiComputer((TileComputer) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
}
