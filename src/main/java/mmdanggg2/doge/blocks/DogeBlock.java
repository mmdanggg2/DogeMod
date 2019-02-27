package mmdanggg2.doge.blocks;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class DogeBlock extends Block {
	
	public DogeBlock(Material material) {
		super(material);
		
		setHardness(1.0f);
		setSoundType(SoundType.METAL);
		setRegistryName(new ResourceLocation(DogeInfo.ID, "doge_block"));
		setUnlocalizedName("doge_block");
		setCreativeTab(Doge.dogeTab);
	}
	
}
