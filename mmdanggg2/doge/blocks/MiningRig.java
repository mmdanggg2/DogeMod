package mmdanggg2.doge.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.IconFlipped;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class MiningRig extends BlockContainer {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static boolean isOn;
	
	public MiningRig(Material material, boolean on) {
		super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		this.isOn = on;
		setHardness(2.0f);
		setStepSound(Block.soundTypeMetal);
		setUnlocalizedName("miningRig");
		if (!on){
			setCreativeTab(Doge.dogeTab);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new MiningRigTileEntity();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		FMLNetworkHandler.openGui(player, Doge.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		return this.getDefaultState().withProperty(FACING, placer.func_174811_aO().getOpposite());
    }
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.func_174811_aO().getOpposite()), 2);
		if (!world.isRemote) {
			DogeLogger.logDebug("MiningRig Placed, Facing " + FACING.getName());
		}
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof IInventory) {
			IInventory inventory = (IInventory) te;
			
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack stack = inventory.getStackInSlotOnClosing(i);
				
				if (stack != null) {
					float spawnX = pos.getX() + world.rand.nextFloat();
					float spawnY = pos.getY() + world.rand.nextFloat();
					float spawnZ = pos.getZ() + world.rand.nextFloat();
					
					EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
					
					float mult = 0.05F;
					
					droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
					droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;
					
					world.spawnEntityInWorld(droppedItem);
				}
			}
		}
		
		super.breakBlock(world, pos, state);
	}
	
	//FIXME 
	/*private static IIcon iconBottom;
	private static IIcon iconTopBackOn;
	private static IIcon iconFrontOn;
	private static IIcon iconSideOn;
	private static IIcon iconTopBackOff;
	private static IIcon iconFrontOff;
	private static IIcon iconSideOff;
	
	@Override
	public IIcon getIcon(int side, int meta) {
		boolean mining = (meta & 4) != 0;

		Map<String, Integer> currRotation = getSidesOfCurrentRotation(meta);
		int front = currRotation.get("front");
		int right = currRotation.get("right");
		int back = currRotation.get("back");
		int left = currRotation.get("left");
		int top = currRotation.get("top");
		int bottom = currRotation.get("bottom");

		meta &= ~4; // set 3rd bit to 0
		
		if (side == bottom) {
			return iconBottom;
		}

		if (mining) {
			if (side == top || side == back) { // top and back are the same
				return iconTopBackOn;
			}
			else if (side == front) {
				return iconFrontOn;
			}
			else if (side == left || side == right) {
				return iconSideOn;
			}
		}
		else {
			if (side == top || side == back) { // top and back are the same
				return iconTopBackOff;
			}
			else if (side == front) {
				return iconFrontOff;
			}
			else if (side == left || side == right) {
				return iconSideOff;
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
	}*/
	
	@Override
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
		TileEntity te = world.getTileEntity(pos);
		
		if (te != null && te instanceof MiningRigTileEntity) {
			MiningRigTileEntity mrte = (MiningRigTileEntity) te;
			
			if (mrte.isMining()) {
				//IBlockState meta = world.getBlockState(pos);
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();
				float facingOffset = 1.05F;
				int meta = 0;
				meta &= ~4; // set 3rd bit to 0
				
				if (meta == 1 && world.isAirBlock(pos.offsetNorth())) {
					world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y + rand.nextFloat(), z + rand.nextFloat(), -0.05D, 0.0D, 0.0D);
					world.spawnParticle(EnumParticleTypes.REDSTONE, x, y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				}
				else if (meta == 3 && world.isAirBlock(pos.offsetSouth())) {
					world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + facingOffset, y + rand.nextFloat(), z + rand.nextFloat(), 0.05D, 0.0D, 0.0D);
					world.spawnParticle(EnumParticleTypes.REDSTONE, x + facingOffset, y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				}
				else if (meta == 2 && world.isAirBlock(pos.offsetEast())) {
					world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + rand.nextFloat(), y + rand.nextFloat(), z, 0.0D, 0.0D, -0.05D);
					world.spawnParticle(EnumParticleTypes.REDSTONE, x + rand.nextFloat(), y + rand.nextFloat(), z, 0.0D, 0.0D, 0.0D);
				}
				else if (meta == 0 && world.isAirBlock(pos.offsetWest())) {
					world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + rand.nextFloat(), y + rand.nextFloat(), z + facingOffset, 0.0D, 0.0D, 0.05D);
					world.spawnParticle(EnumParticleTypes.REDSTONE, x + rand.nextFloat(), y + rand.nextFloat(), z + facingOffset, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
	
	public static Map<String, Integer> getSidesOfCurrentRotation(IBlockState state) {
		Map<String, Integer> sides = new HashMap<String, Integer>();
		
		sides.put("bottom", 0);
		sides.put("top", 1);
		
		//FIXME state &= ~4; // set 3rd bit to 0
		int[] sideOrder;
		switch (0) {
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
		
		sides.put("front", sideOrder[0]);
		sides.put("left", sideOrder[1]);
		sides.put("back", sideOrder[2]);
		sides.put("right", sideOrder[3]);
		
		return sides;
	}
	
	public static void setMining(Boolean mining, World world, BlockPos pos) {
		//FIXME world.setBlockState(pos, Doge.miningRig.getDefaultState().withProperty(MINING, mining), 3);
	}
	
	protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }
	
	public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
}
