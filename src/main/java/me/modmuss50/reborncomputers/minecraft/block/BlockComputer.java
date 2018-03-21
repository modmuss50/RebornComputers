package me.modmuss50.reborncomputers.minecraft.block;

import me.modmuss50.reborncomputers.minecraft.RebornComputersMod;
import me.modmuss50.reborncomputers.minecraft.client.GuiHandler;
import me.modmuss50.reborncomputers.minecraft.tile.TileComputer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockComputer extends BlockContainer {

	public BlockComputer() {
		super(Material.ROCK);
		setCreativeTab(CreativeTabs.REDSTONE);
		setUnlocalizedName("computer");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote){
			playerIn.openGui(RebornComputersMod.INSTANCE, GuiHandler.COMPUTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileComputer();
	}
}
