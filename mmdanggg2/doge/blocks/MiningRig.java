package mmdanggg2.doge.blocks;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class MiningRig extends BlockContainer {
	
	public MiningRig(Material material) {
		super(material);
		
		setHardness(1.0f);
		setStepSound(Block.soundTypeMetal);
		setBlockName("miningRig");
		setCreativeTab(Doge.dogeTab);
		setBlockTextureName(BasicInfo.NAME.toLowerCase() + ":miningRig");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new MiningRigTileEntity();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		FMLNetworkHandler.openGui(player, Doge.instance, 0, world, x, y, z);
		return true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof IInventory) {
			IInventory inventory = (IInventory) te;
			
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack stack = inventory.getStackInSlotOnClosing(i);
				
				if (stack != null) {
					float spawnX = x + world.rand.nextFloat();
					float spawnY = y + world.rand.nextFloat();
					float spawnZ = z + world.rand.nextFloat();
					
					EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
					
					float mult = 0.05F;
					
					droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
					droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;
					
					world.spawnEntityInWorld(droppedItem);
				}
			}
		}
		
		super.breakBlock(world, x, y, z, block, meta);
	}
}
