package mmdanggg2.doge.blocks;

import java.util.Random;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class MiningRig extends BlockContainer {
	
	public MiningRig(Material material) {
		super(material);
		
		setHardness(2.0f);
		setStepSound(Block.soundTypeMetal);
		setBlockName("miningRig");
		setCreativeTab(Doge.dogeTab);
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if (!world.isRemote) {
			DogeLogger.logDebug("MiningRig Placed, meta = " + rotation);
		}
		
		if (rotation == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		}
		
		if (rotation == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		}
		
		if (rotation == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		
		if (rotation == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
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

	private static IIcon iconBottom;
	private static IIcon iconTopBackOn;
	private static IIcon iconFrontOn;
	private static IIcon iconSideOn;
	private static IIcon iconTopBackOff;
	private static IIcon iconFrontOff;
	private static IIcon iconSideOff;
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0) {
			return iconBottom;
		}
		
		int[] sideOrder;
		
		boolean mining = (meta & 4) != 0;
		meta &= ~4; // set 3rd bit to 0

		switch (meta) {
		case 0:
			sideOrder = new int[] { 2, 5, 3, 4 };
			break;
		case 1:
			sideOrder = new int[] { 5, 2, 4, 3 };
			break;
		case 2:
			sideOrder = new int[] { 3, 5, 2, 4 };
			break;
		case 3:
			sideOrder = new int[] { 4, 2, 5, 3 };
			break;
		default:
			sideOrder = new int[] { 2, 5, 3, 4 };
			break;
		}
		
		int front = sideOrder[0];
		int right = sideOrder[1];
		int back = sideOrder[2];
		int left = sideOrder[3];
		
		if (mining) {
			if (side == 1 || side == back) { // top and back are the same
				return iconTopBackOn;
			}
			else if (side == front) {
				return new IconFlipped(iconFrontOn, meta < 2, false);
			}
			else if (side == left || side == right) {
				return new IconFlipped(iconSideOn, side == right, false);
			}
		}
		else {
			if (side == 1 || side == back) { // top and back are the same
				return iconTopBackOff;
			}
			else if (side == front) {
				return new IconFlipped(iconFrontOff, meta < 2, false);
			}
			else if (side == left || side == right) {
				return new IconFlipped(iconSideOff, side == right, false);
			}
		}
		return null;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		String name = DogeInfo.NAME.toLowerCase();
		iconBottom = icon.registerIcon(name + ":miningRigBottom");
		iconTopBackOn = icon.registerIcon(name + ":miningRigTopBackOn");
		iconFrontOn = icon.registerIcon(name + ":miningRigFrontOn");
		iconSideOn = icon.registerIcon(name + ":miningRigSideOn");
		iconTopBackOff = icon.registerIcon(name + ":miningRigTopBackOff");
		iconFrontOff = icon.registerIcon(name + ":miningRigFrontOff");
		iconSideOff = icon.registerIcon(name + ":miningRigSideOff");
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		TileEntity te = world.getTileEntity(x, y, z);
		
		if (te != null && te instanceof MiningRigTileEntity) {
			MiningRigTileEntity mrte = (MiningRigTileEntity) te;
			
			if (mrte.isMining()) {
				int meta = world.getBlockMetadata(x, y, z);
				float facingOffset = 1.05F;
				
				meta &= ~4; // set 3rd bit to 0
				
				if (meta == 1 && world.isAirBlock(x - 1, y, z)) {
					world.spawnParticle("smoke", x, y + rand.nextFloat(), z + rand.nextFloat(), -0.05D, 0.0D, 0.0D);
					world.spawnParticle("reddust", x, y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				}
				else if (meta == 3 && world.isAirBlock(x + 1, y, z)) {
					world.spawnParticle("smoke", x + facingOffset, y + rand.nextFloat(), z + rand.nextFloat(), 0.05D, 0.0D, 0.0D);
					world.spawnParticle("reddust", x + facingOffset, y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				}
				else if (meta == 2 && world.isAirBlock(x, y, z - 1)) {
					world.spawnParticle("smoke", x + rand.nextFloat(), y + rand.nextFloat(), z, 0.0D, 0.0D, -0.05D);
					world.spawnParticle("reddust", x + rand.nextFloat(), y + rand.nextFloat(), z, 0.0D, 0.0D, 0.0D);
				}
				else if (meta == 0 && world.isAirBlock(x, y, z + 1)) {
					world.spawnParticle("smoke", x + rand.nextFloat(), y + rand.nextFloat(), z + facingOffset, 0.0D, 0.0D, 0.05D);
					world.spawnParticle("reddust", x + rand.nextFloat(), y + rand.nextFloat(), z + facingOffset, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}
