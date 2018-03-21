package me.modmuss50.reborncomputers.minecraft.tile;

import me.modmuss50.reborncomputers.computer.Computer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import reborncore.common.tile.TileLegacyMachineBase;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileComputer extends TileLegacyMachineBase implements ITickable {

	Computer computer;
	UUID uuid;

	@Nullable
	public Computer getComputer(){
		if(computer == null){
			if(uuid == null){
				uuid = UUID.randomUUID();
			}
			computer = new Computer(uuid);
		}
		return computer;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("uuid")){
			uuid = UUID.fromString(compound.getString("uuid"));
			getComputer(); //Loads the computer
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(uuid != null){
			compound.setString("uuid", uuid.toString());
		}
		return super.writeToNBT(compound);
	}
}
