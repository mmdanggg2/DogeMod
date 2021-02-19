package mmdanggg2.doge.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.init.DogeBlocks;
import mmdanggg2.doge.init.DogeTileEntityTypes;
import mmdanggg2.doge.tileentitiy.MiningRigTileEntity;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;

public class MiningRig extends ContainerBlock {
	
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty MINING = BooleanProperty.create("mining");
	private static boolean isChangingBlock;
	
	public MiningRig() {
		super(Block.Properties.create(Material.IRON, MaterialColor.GRAY).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL));
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(MINING, false));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, MINING);
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return DogeTileEntityTypes.MINING_RIG.get().create();
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
		return Container.calcRedstone(world.getTileEntity(pos));
	}
	
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (!world.isRemote) {
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity instanceof MiningRigTileEntity && player instanceof ServerPlayerEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (MiningRigTileEntity) tileEntity, pos);
			} else {
				return ActionResultType.PASS;
			}
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof MiningRigTileEntity) {
				((MiningRigTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.isIn(newState.getBlock())) {
			TileEntity te = world.getTileEntity(pos);
			if (te != null && te instanceof IInventory) {
				InventoryHelper.dropInventoryItems(world, pos, (IInventory) te);
				world.updateComparatorOutputLevel(pos, this);
			}
		}
		super.onReplaced(state, world, pos, newState, isMoving);
	}
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (state.get(MINING)) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			float facingOffset = 1.05F;
			
			Direction enumfacing = state.get(FACING);
			
			if (enumfacing == Direction.EAST && world.isAirBlock(pos.east())) {
				world.addParticle(ParticleTypes.SMOKE, x, y + rand.nextFloat(), z + rand.nextFloat(), -0.05D, 0.0D, 0.0D);
				world.addParticle(RedstoneParticleData.REDSTONE_DUST, x, y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			} else if (enumfacing == Direction.WEST && world.isAirBlock(pos.west())) {
				world.addParticle(ParticleTypes.SMOKE, x + facingOffset, y + rand.nextFloat(), z + rand.nextFloat(), 0.05D, 0.0D, 0.0D);
				world.addParticle(RedstoneParticleData.REDSTONE_DUST, x + facingOffset, y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			} else if (enumfacing == Direction.SOUTH && world.isAirBlock(pos.south())) {
				world.addParticle(ParticleTypes.SMOKE, x + rand.nextFloat(), y + rand.nextFloat(), z, 0.0D, 0.0D, -0.05D);
				world.addParticle(RedstoneParticleData.REDSTONE_DUST, x + rand.nextFloat(), y + rand.nextFloat(), z, 0.0D, 0.0D, 0.0D);
			} else if (enumfacing == Direction.NORTH && world.isAirBlock(pos.north())) {
				world.addParticle(ParticleTypes.SMOKE, x + rand.nextFloat(), y + rand.nextFloat(), z + facingOffset, 0.0D, 0.0D, 0.05D);
				world.addParticle(RedstoneParticleData.REDSTONE_DUST, x + rand.nextFloat(), y + rand.nextFloat(), z + facingOffset, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	public static Map<String, Direction> getSidesOfCurrentRotation(BlockState state) {
		Map<String, Direction> sides = new HashMap<String, Direction>();
		
		sides.put("bottom", Direction.DOWN);
		sides.put("top", Direction.UP);
		
		Direction enumFacing = (Direction) state.get(FACING);
		
		Direction[] sideOrder;
		if (enumFacing == Direction.NORTH) {
			sideOrder = new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };
		} else if (enumFacing == Direction.EAST) {
			sideOrder = new Direction[] { Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH };
		} else if (enumFacing == Direction.SOUTH) {
			sideOrder = new Direction[] { Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST };
		} else if (enumFacing == Direction.WEST) {
			sideOrder = new Direction[] { Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH };
		} else {
			sideOrder = new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };
		}
		
		sides.put("front", sideOrder[0]);
		sides.put("left", sideOrder[1]);
		sides.put("back", sideOrder[2]);
		sides.put("right", sideOrder[3]);
		
		return sides;
	}
	
	public static void setMining(Boolean mining, World world, BlockPos pos) {
		BlockState iblockstate = world.getBlockState(pos);
		TileEntity tileentity = world.getTileEntity(pos);
		
		isChangingBlock = true;
		
		world.setBlockState(pos, DogeBlocks.MINING_RIG.get().getDefaultState().with(FACING, iblockstate.get(FACING)).with(MINING, mining), 3);
		
		isChangingBlock = false;
		
		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(pos, tileentity);
		}
	}
}
