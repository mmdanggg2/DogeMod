package mmdanggg2.doge.init;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.tileentitiy.MiningRigTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DogeTileEntityTypes {
	
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Doge.ID);
	
	public static final RegistryObject<TileEntityType<MiningRigTileEntity>> MINING_RIG = TILE_ENTITY_TYPES.register("mining_rig", ()-> TileEntityType.Builder.create(MiningRigTileEntity::new, DogeBlocks.MINING_RIG.get()).build(null));
}
