package me.modmuss50.reborncomputers.minecraft.client.gui;

import me.modmuss50.reborncomputers.computer.Computer;
import me.modmuss50.reborncomputers.computer.Monitor;
import me.modmuss50.reborncomputers.minecraft.tile.TileComputer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import reborncore.client.guibuilder.GuiBuilder;

public class GuiComputer extends GuiContainer {
	GuiBuilder builder = new GuiBuilder(GuiBuilder.defaultTextureSheet);

    TileComputer tileComputer;
    Computer computer;
    Monitor monitor;

	public GuiComputer(TileComputer tileComputer) {
		super(new ContainerComputer(tileComputer));
		this.tileComputer = tileComputer;
		this.computer = tileComputer.getComputer();
		//TODO use a client side version of this, as the client should not be using getComputer!
		this.monitor = computer.getMontior(0);
	}

	@Override
	public void initGui() {
		super.initGui();

		xSize = 200;
		ySize = 200;

		guiLeft = (this.width - this.xSize) / 2;
		guiTop = (this.height - this.ySize) / 2;
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
		super.drawScreen(mouseX, mouseY, partialTicks);
		FontRenderer fontRenderer = this.fontRenderer;

		monitor.walkData(point -> fontRenderer.drawString(monitor.getCharAt(point) + "", guiLeft +  (point.getX() * 6), guiTop + (point.getY() * 7), -1));
	}

	@Override
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		System.out.println("Starting computer " + computer.getReference());
		if(!computer.isRunning()){
			computer.start();
		}
		super.handleMouseClick(slotIn, slotId, mouseButton, type);
	}
}
