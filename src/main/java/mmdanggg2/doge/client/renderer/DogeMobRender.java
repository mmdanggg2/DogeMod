package mmdanggg2.doge.client.renderer;

import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.entities.DogeMob;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DogeMobRender extends WolfRenderer {
	
	private static final ResourceLocation dogeNorm = new ResourceLocation(DogeInfo.NAME.toLowerCase(), "textures/entity/doge/doge.png");
	private static final ResourceLocation dogeTame = new ResourceLocation(DogeInfo.NAME.toLowerCase(), "textures/entity/doge/doge_tame.png");
	private static final ResourceLocation dogeAngry = new ResourceLocation(DogeInfo.NAME.toLowerCase(), "textures/entity/doge/doge_angry.png");
	private static final ResourceLocation dogeCollar = new ResourceLocation(DogeInfo.NAME.toLowerCase(), "textures/entity/doge/doge_collar.png");
	
	public DogeMobRender(EntityRendererManager renderMan) {
		super(renderMan);
		this.addLayer(new DogeMobRenderLayerCollar(this));
	}
	
	@Override
	public ResourceLocation getEntityTexture(WolfEntity wolf)
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
	
	public class DogeMobRenderLayerCollar extends WolfCollarLayer {
		private final DogeMobRender dogeRenderer;
		
		public DogeMobRenderLayerCollar(DogeMobRender dogeRender) {
			super(dogeRender);
			this.dogeRenderer = dogeRender;
		}
		
		@Override
		public void doRenderLayer(WolfEntity wolf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	    {
	        if (wolf.isTamed() && !wolf.isInvisible())
	        {
	            this.dogeRenderer.bindTexture(DogeMobRender.dogeCollar);
	            float[] afloat = wolf.getCollarColor().getColorComponentValues();
	            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
	            this.dogeRenderer.getMainModel().render(wolf, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	        }
	    }
	}

	
}