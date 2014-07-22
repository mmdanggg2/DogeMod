package mmdanggg2.doge.client.interfaces;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GUIHandler implements IGuiHandler {
	
	public GUIHandler() {
		NetworkRegistry.instance().registerGuiHandler(Doge.instance, this);
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
		case 0:
			TileEntity te = world.getBlockTileEntity(x, y, z);
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
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof MiningRigTileEntity) {
				return new MiningRigContainer(player.inventory, (MiningRigTileEntity) te);
			}
			break;
		}
		return null;
	}
	
}
