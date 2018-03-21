package me.modmuss50.reborncomputers.minecraft.packets;

import me.modmuss50.reborncomputers.computer.Monitor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import reborncore.common.network.ExtendedPacketBuffer;
import reborncore.common.network.INetworkPacket;

import java.io.IOException;

public class PacketSyncMonitorData implements INetworkPacket<PacketSyncMonitorData> {

	String computer;
	int monitorID;
	Monitor monitor;

	@Override
	public void writeData(ExtendedPacketBuffer buffer) throws IOException {
		buffer.writeInt(computer.length());
		buffer.writeString(computer);
		buffer.writeInt(monitorID);
		buffer.writeCompoundTag((NBTTagCompound) monitor.serializeNBT());
	}

	@Override
	public void readData(ExtendedPacketBuffer buffer) throws IOException {
		computer = buffer.readString(buffer.readInt());
		monitorID = buffer.readInt();
		monitor = new Monitor(buffer.readCompoundTag());
	}

	@Override
	public void processData(PacketSyncMonitorData message, MessageContext context) {

	}
}
