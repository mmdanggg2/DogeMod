package mmdanggg2.doge.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class MiningRig extends BlockContainer {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private final boolean isOn;
    private static boolean isChangingBlock;
	
	public MiningRig(Material material, boolean on) {
		super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		this.isOn = on;
		setHardness(2.0f);
		setSoundType(SoundType.METAL);
		setUnlocalizedName("miningRig");
		if (!on){
			setCreativeTab(Doge.dogeTab);
			setRegistryName("miningRig");
		}
		else {
			setRegistryName("miningRigOn");
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new MiningRigTileEntity();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		FMLNetworkHandler.openGui(player, Doge.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing direction = placer.getHorizontalFacing().getOpposite();
        world.setBlockState(pos, state.withProperty(FACING, direction), 2);
        
        if (stack.hasDisplayName())
        {
            TileEntity tileentity = world.getTileEntity(pos);

            if (tileentity instanceof MiningRigTileEntity)
            {
                ((MiningRigTileEntity)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
        
		if (!world.isRemote) {
			DogeLogger.logDebug("MiningRig Placed, Facing " + direction.getName());
		}
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
		if (!world.isRemote)
        {
            BlockPos blkposN = pos.north();
            IBlockState blkstN = world.getBlockState(blkposN);
            Block blkN = blkstN.getBlock();
            BlockPos blkposS = pos.south();
            IBlockState blkstS = world.getBlockState(blkposS);
            Block blkS = blkstS.getBlock();
            BlockPos blkposW = pos.west();
            IBlockState blkstW = world.getBlockState(blkposW);
            Block blkW = blkstW.getBlock();
            BlockPos blkposE = pos.east();
            IBlockState blkstE = world.getBlockState(blkposE);
            Block blkE = blkstE.getBlock();
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && blkN.isSideSolid(blkstN, world, blkposN, EnumFacing.SOUTH) && !blkS.isSideSolid(blkstS, world, blkposS, EnumFacing.NORTH))
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && blkS.isSideSolid(blkstS, world, blkposS, EnumFacing.NORTH) && !blkN.isSideSolid(blkstN, world, blkposN, EnumFacing.SOUTH))
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && blkW.isSideSolid(blkstW, world, blkposW, EnumFacing.EAST) && !blkE.isSideSolid(blkstE, world, blkposE, EnumFacing.WEST))
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && blkE.isSideSolid(blkstE, world, blkposE, EnumFacing.WEST) && !blkW.isSideSolid(blkstW, world, blkposW, EnumFacing.EAST))
            {
                enumfacing = EnumFacing.WEST;
            }

            world.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (!isChangingBlock){
			TileEntity te = world.getTileEntity(pos);
			if (te != null && te instanceof IInventory) {
				IInventory inventory = (IInventory) te;
				InventoryHelper.dropInventoryItems(world, pos, inventory);
	            world.updateComparatorOutputLevel(pos, this);
			}
		}
		
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (this.isOn) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			float facingOffset = 1.05F;
			
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
	
			if (enumfacing == EnumFacing.EAST && world.isAirBlock(pos.east())) {
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y + rand.nextFloat(), z + rand.nextFloat(), -0.05D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.REDSTONE, x, y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			}
			else if (enumfacing == EnumFacing.WEST && world.isAirBlock(pos.west())) {
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + facingOffset, y + rand.nextFloat(), z + rand.nextFloat(), 0.05D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.REDSTONE, x + facingOffset, y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			}
			else if (enumfacing == EnumFacing.SOUTH && world.isAirBlock(pos.south())) {
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + rand.nextFloat(), y + rand.nextFloat(), z, 0.0D, 0.0D, -0.05D);
				world.spawnParticle(EnumParticleTypes.REDSTONE, x + rand.nextFloat(), y + rand.nextFloat(), z, 0.0D, 0.0D, 0.0D);
			}
			else if (enumfacing == EnumFacing.NORTH && world.isAirBlock(pos.north())) {
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + rand.nextFloat(), y + rand.nextFloat(), z + facingOffset, 0.0D, 0.0D, 0.05D);
				world.spawnParticle(EnumParticleTypes.REDSTONE, x + rand.nextFloat(), y + rand.nextFloat(), z + facingOffset, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	public static Map<String, EnumFacing> getSidesOfCurrentRotation(IBlockState state) {
		Map<String, EnumFacing> sides = new HashMap<String, EnumFacing>();
		
		sides.put("bottom", EnumFacing.DOWN);
		sides.put("top", EnumFacing.UP);
		
		EnumFacing enumFacing = (EnumFacing)state.getValue(FACING);
		
		EnumFacing[] sideOrder;
		if(enumFacing == EnumFacing.NORTH) {
			sideOrder = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
		}
		else if (enumFacing == EnumFacing.EAST) {
			sideOrder = new EnumFacing[] { EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.NORTH };
		}
		else if (enumFacing == EnumFacing.SOUTH){
			sideOrder = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.EAST };
		}
		else if (enumFacing == EnumFacing.WEST){
			sideOrder = new EnumFacing[] { EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH };
		}
		else{sideOrder = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };}
		
		sides.put("front", sideOrder[0]);
		sides.put("left", sideOrder[1]);
		sides.put("back", sideOrder[2]);
		sides.put("right", sideOrder[3]);
		
		return sides;
	}
	
	public static void setMining(Boolean mining, World world, BlockPos pos) {
		IBlockState iblockstate = world.getBlockState(pos);
        TileEntity tileentity = world.getTileEntity(pos);
        
        isChangingBlock = true;
        
        if (mining)
        {
            world.setBlockState(pos, Doge.miningRigOn.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            world.setBlockState(pos, Doge.miningRigOn.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        else
        {
            world.setBlockState(pos, Doge.miningRig.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            world.setBlockState(pos, Doge.miningRig.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        
        isChangingBlock = false;

        if (tileentity != null)
        {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
	}
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}
