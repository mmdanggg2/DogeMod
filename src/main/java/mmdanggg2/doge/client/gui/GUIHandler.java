package mmdanggg2.doge.client.gui;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.tileentitiy.MiningRigTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GUIHandler implements IGuiHandler {
	
	public GUIHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(Doge.instance, this);
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
		case 0:
			BlockPos pos = new BlockPos(x, y, z);
			TileEntity te = world.getTileEntity(pos);
			if (te != null && te instanceof MiningRigTileEntity) {
				return new MiningRigGUI(player.inventory, (MiningRigTileEntity) te);
			}
			break;
		}
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
		case 0:
			BlockPos pos = new BlockPos(x, y, z);
			TileEntity te = world.getTileEntity(pos);
			if (te != null && te instanceof MiningRigTileEntity) {
				return new MiningRigContainer(player.inventory, (MiningRigTileEntity) te);
			}
			break;
		}
		return null;
	}
	
}
