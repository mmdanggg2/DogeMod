package mmdanggg2.doge.blocks;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class DogeBlock extends Block {
	
	public DogeBlock(int id, Material material) {
		super(id, material);
		
		setHardness(1.0f);
		setStepSound(Block.soundMetalFootstep);
		setUnlocalizedName("dogeBlock");
		setCreativeTab(Doge.dogeTab);
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeblock");
	}
	
}
