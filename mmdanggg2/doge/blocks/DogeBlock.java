package mmdanggg2.doge.blocks;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class DogeBlock extends Block {
	
	public DogeBlock(Material material) {
		super(material);
		
		setHardness(1.0f);
		setStepSound(Block.soundTypeMetal);
		setBlockName("dogeBlock");
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockTextureName(BasicInfo.NAME.toLowerCase() + ":dogeblock");
	}
	
}
