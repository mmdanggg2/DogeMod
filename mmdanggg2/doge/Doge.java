package mmdanggg2.doge;
 
import mmdanggg2.doge.blocks.DogeBlock;
import mmdanggg2.doge.entities.DogeMob;
import mmdanggg2.doge.items.DogeAxe;
import mmdanggg2.doge.items.DogeBoots;
import mmdanggg2.doge.items.DogeChestplate;
import mmdanggg2.doge.items.DogeHelmet;
import mmdanggg2.doge.items.DogeHoe;
import mmdanggg2.doge.items.DogeLeggings;
import mmdanggg2.doge.items.DogePickaxe;
import mmdanggg2.doge.items.DogeShovel;
import mmdanggg2.doge.items.DogeSword;
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
	
	//Inits
	
	public static EnumToolMaterial dogeToolMat = EnumHelper.addToolMaterial("Doge", 3, 600, 20.0F, 4.0F, 30);
	public static EnumArmorMaterial dogeArmorMat = EnumHelper.addArmorMaterial("Doge", 30, new int[]{5, 10, 8, 5}, 30);
	

	//Doge Tools
	public final static Item dogePickaxe = new DogePickaxe(5001, dogeToolMat);
	public final static Item dogeAxe = new DogeAxe(5002, dogeToolMat);
	public final static Item dogeShovel = new DogeShovel(5003, dogeToolMat);
	public final static Item dogeHoe = new DogeHoe(5004, dogeToolMat);
	public final static Item dogeSword = new DogeSword(5005, dogeToolMat);
	
	//Doge Armour
	public final static Item dogeHelmet = new DogeHelmet(5006, dogeArmorMat, 5, 0);
	public final static Item dogeChestplate = new DogeChestplate(5007, dogeArmorMat, 5, 1);
	public final static Item dogeLeggings = new DogeLeggings(5008, dogeArmorMat, 5, 2);
	public final static Item dogeBoots = new DogeBoots(5009, dogeArmorMat, 5, 3);
	
	
	//Other
	public final static Block dogeBlock = new DogeBlock(500, Material.ground);
	
	public final static Item dogecoin = new Dogecoin(5000);
	
	public final static Item dogeLauncher = new DogeLauncher(5010);
	
	
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
                
                dogeToolMat.customCraftingMaterial = dogecoin;
                dogeArmorMat.customCraftingMaterial = dogecoin;
                
                //Items
                GameRegistry.registerItem(dogecoin, "dogecoin");
                LanguageRegistry.addName(dogecoin, "Dogecoin");

                GameRegistry.registerItem(dogeLauncher, "dogeLauncher");
                LanguageRegistry.addName(dogeLauncher, "Doge Launcher");
                
                //Doge Tools
                GameRegistry.registerItem(dogePickaxe, "dogePickaxe");
                LanguageRegistry.addName(dogePickaxe, "such mine");

                GameRegistry.registerItem(dogeAxe, "dogeAxe");
                LanguageRegistry.addName(dogeAxe, "many chop");

                GameRegistry.registerItem(dogeShovel, "dogeShovel");
                LanguageRegistry.addName(dogeShovel, "much dig");

                GameRegistry.registerItem(dogeHoe, "dogeHoe");
                LanguageRegistry.addName(dogeHoe, "very farm");

                GameRegistry.registerItem(dogeSword, "dogeSword");
                LanguageRegistry.addName(dogeSword, "wow attack");
                
                //Doge Armour
                GameRegistry.registerItem(dogeHelmet, "dogeHelmet");
                LanguageRegistry.addName(dogeHelmet, "many protect face");

                GameRegistry.registerItem(dogeChestplate, "dogeChestplate");
                LanguageRegistry.addName(dogeChestplate, "much chest armour");

                GameRegistry.registerItem(dogeLeggings, "dogeLeggings");
                LanguageRegistry.addName(dogeLeggings, "very leg defend");

                GameRegistry.registerItem(dogeBoots, "dogeBoots");
                LanguageRegistry.addName(dogeBoots, "wow shoes");
                
                
                //Blocks
                GameRegistry.registerBlock(dogeBlock, "dogeBlock");
                LanguageRegistry.addName(dogeBlock, "Doge Block");
                
                
                //Recipes
                GameRegistry.addRecipe(new ItemStack(dogecoin, 1), " G ", "GDG", " G ", 
                		'D', new ItemStack(Item.diamond), 'G', new ItemStack(Item.ingotGold));
                
                GameRegistry.addRecipe(new ItemStack(dogeBlock), "###", "###", "###", 
                		'#', new ItemStack(dogecoin));
                
                GameRegistry.addShapelessRecipe(new ItemStack(dogecoin, 9), new ItemStack(dogeBlock));
                
                //Doge Tool Recipes
                GameRegistry.addRecipe(new ItemStack(dogePickaxe), "###", " S ", " S ", 
                		'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
                
                GameRegistry.addRecipe(new ItemStack(dogeAxe), " ##", " S#", " S ", 
                		'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
                
                GameRegistry.addRecipe(new ItemStack(dogeShovel), " # ", " S ", " S ", 
                		'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
                
                GameRegistry.addRecipe(new ItemStack(dogeHoe), " ##", " S ", " S ", 
                		'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
                
                GameRegistry.addRecipe(new ItemStack(dogeSword), " # ", " # ", " S ", 
                		'#', new ItemStack(dogecoin), 'S', new ItemStack(Item.stick));
                
                //Doge Armour Recipes
                GameRegistry.addRecipe(new ItemStack(dogeHelmet), "###", "# #",
                		'#', new ItemStack(dogecoin));
                
                GameRegistry.addRecipe(new ItemStack(dogeChestplate), "# #", "###", "###", 
                		'#', new ItemStack(dogecoin));
                
                GameRegistry.addRecipe(new ItemStack(dogeLeggings), "###", "# #", "# #", 
                		'#', new ItemStack(dogecoin));
                
                GameRegistry.addRecipe(new ItemStack(dogeBoots), "# #", "# #",
                		'#', new ItemStack(dogecoin));
                
                
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