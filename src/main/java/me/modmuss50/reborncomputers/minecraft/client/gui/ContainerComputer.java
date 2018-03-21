package me.modmuss50.reborncomputers.minecraft.client.gui;

import me.modmuss50.reborncomputers.minecraft.tile.TileComputer;
import reborncore.common.container.RebornContainer;

public class ContainerComputer extends RebornContainer {

	TileComputer tileComputer;

	public ContainerComputer(TileComputer tileComputer) {
		super(tileComputer);
		this.tileComputer = tileComputer;
	}
}
