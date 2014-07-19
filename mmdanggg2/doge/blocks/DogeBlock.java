package mmdanggg2.doge.blocks;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class DogeBlock extends Block {
	
	public DogeBlock(Material material) {
		super(material);
		
		setHardness(1.0f);
		setStepSound(Block.soundTypeMetal);
		setBlockName("dogeBlock");
		setCreativeTab(Doge.dogeTab);
		setBlockTextureName(BasicInfo.NAME.toLowerCase() + ":dogeblock");
	}
	
}
