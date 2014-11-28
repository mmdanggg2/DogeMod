package mmdanggg2.doge.renderer;

import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.entities.DogeMob;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DogeMobRender extends RenderWolf {
	
	private static final ResourceLocation dogeNorm = new ResourceLocation(DogeInfo.NAME.toLowerCase(), "textures/entity/doge/doge.png");
	private static final ResourceLocation dogeTame = new ResourceLocation(DogeInfo.NAME.toLowerCase(), "textures/entity/doge/doge_tame.png");
	private static final ResourceLocation dogeAngry = new ResourceLocation(DogeInfo.NAME.toLowerCase(), "textures/entity/doge/doge_angry.png");
	private static final ResourceLocation dogeCollar = new ResourceLocation(DogeInfo.NAME.toLowerCase(), "textures/entity/doge/doge_collar.png");
	
	public DogeMobRender(RenderManager par1RenderManager, ModelBase par2ModelBase, float par3) {
		super(par1RenderManager, par2ModelBase, par3);
	}
	
	//FIXME
	/*@Override
	protected int shouldRenderPass(EntityWolf par1EntityWolf, int par2, float par3)
	{
		float f1;
		
		if (par2 == 0 && par1EntityWolf.getWolfShaking())
		{
			f1 = par1EntityWolf.getBrightness(par3) * par1EntityWolf.getShadingWhileShaking(par3);
			this.bindTexture(dogeNorm);
			GL11.glColor3f(f1, f1, f1);
			return 1;
		}
		else if (par2 == 1 && par1EntityWolf.isTamed())
		{
			this.bindTexture(dogeCollar);
			f1 = 1.0F;
			int j = par1EntityWolf.getCollarColor();
			GL11.glColor3f(f1 * EntitySheep.fleeceColorTable[j][0], f1 * EntitySheep.fleeceColorTable[j][1], f1 * EntitySheep.fleeceColorTable[j][2]);
			return 1;
		}
		else
		{
			return -1;
		}
	}*/
	
	@Override
	protected ResourceLocation getEntityTexture(EntityWolf wolf)
	{
		DogeMob doge = null;
		if (wolf instanceof DogeMob) {
			doge = (DogeMob) wolf;
		}
		else {
			super.getEntityTexture(wolf);
		}

		ResourceLocation returnedTex = dogeNorm;
		
		if (doge.isTamed()) {
			returnedTex = dogeTame;
		}
		else if (doge.isAngry()) {
			returnedTex = dogeAngry;
		}
		return returnedTex;
	}
	
}