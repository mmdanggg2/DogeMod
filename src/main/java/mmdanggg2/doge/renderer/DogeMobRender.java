package mmdanggg2.doge.renderer;

import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.entities.DogeMob;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
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
		this.addLayer(new DogeMobRenderLayerCollar(this));
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityWolf wolf)
	{
		DogeMob doge = null;
		if (wolf instanceof DogeMob) {
			doge = (DogeMob) wolf;
		}
		else {
			return super.getEntityTexture(wolf);
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
	
	public class DogeMobRenderLayerCollar extends LayerWolfCollar {
		private final DogeMobRender dogeRenderer;
		
		public DogeMobRenderLayerCollar(DogeMobRender dogeRender) {
			super(dogeRender);
			this.dogeRenderer = dogeRender;
		}
		
		public void func_177145_a(EntityWolf wolf, float p_177145_2_, float p_177145_3_, float p_177145_4_, float p_177145_5_, float p_177145_6_, float p_177145_7_, float p_177145_8_)
	    {
	        if (wolf.isTamed() && !wolf.isInvisible())
	        {
	            this.dogeRenderer.bindTexture(DogeMobRender.dogeCollar);
	            EnumDyeColor enumdyecolor = EnumDyeColor.byMetadata(wolf.getCollarColor().getMetadata());
	            float[] afloat = EntitySheep.func_175513_a(enumdyecolor);
	            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
	            this.dogeRenderer.getMainModel().render(wolf, p_177145_2_, p_177145_3_, p_177145_5_, p_177145_6_, p_177145_7_, p_177145_8_);
	        }
	    }
	}

	
}