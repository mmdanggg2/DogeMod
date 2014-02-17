package mmdanggg2.doge;
 
import mmdanggg2.doge.blocks.DogeBlock;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.items.Dogecoin;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
 
@Mod(modid=BasicInfo.ID, name=BasicInfo.NAME, version=BasicInfo.VER)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Doge {
	
	//Other
	public final static Block dogeBlock = new DogeBlock(500, Material.ground);
	
	public final static Item dogecoin = new Dogecoin(5000);
	
	
        // The instance of your mod that Forge uses.
        @Instance(BasicInfo.NAME)
        public static Doge instance;
       
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide=BasicInfo.CLIENTPROXY + "ClientProxy", serverSide=BasicInfo.COMMONPROXY + "CommonProxy")
        public static CommonProxy proxy;
       
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
                // Stub Method
        }
       
        @EventHandler
        public void load(FMLInitializationEvent event) {
                proxy.registerRenderers();
                
                //Items
                GameRegistry.registerItem(dogecoin, "dogecoin");
                LanguageRegistry.addName(dogecoin, "Dogecoin");
                
                
                //Blocks
                GameRegistry.registerBlock(dogeBlock, "dogeBlock");
                LanguageRegistry.addName(dogeBlock, "Doge Block");
                
                
                //Recipes
                GameRegistry.addRecipe(new ItemStack(dogecoin, 1), " G ", "GDG", " G ", 
                		'D', new ItemStack(Item.diamond), 'G', new ItemStack(Item.ingotGold));
                
                GameRegistry.addRecipe(new ItemStack(dogeBlock), "###", "###", "###", 
                		'#', new ItemStack(dogecoin));
                
                GameRegistry.addShapelessRecipe(new ItemStack(dogecoin, 9), new ItemStack(dogeBlock));
                
                
                //Mobs
                registerEntity(DogeMob.class, "DogeMob", 0xeaeae9, 0xc99a03);
                LanguageRegistry.instance().addStringLocalization("entity.DogeMob.name", "Doge");
                
                
        }
       
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
        
    public void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
        int id = EntityRegistry.findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
        EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
    }

    public void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, BiomeGenBase[] biomes) {
            if (spawnProb > 0) {
                    EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.creature, biomes);
            }
    }
       
}