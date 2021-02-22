package mmdanggg2.doge.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entities.ShibaEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;

public class ShibaRender extends WolfRenderer {
	
	private static final ResourceLocation SHIBA_NORM_TEX = new ResourceLocation(Doge.ID, "textures/entity/shiba/shiba.png");
	private static final ResourceLocation SHIBA_TAME_TEX = new ResourceLocation(Doge.ID, "textures/entity/shiba/shiba_tame.png");
	private static final ResourceLocation SHIBA_ANGRY_TEX = new ResourceLocation(Doge.ID, "textures/entity/shiba/shiba_angry.png");
	private static final ResourceLocation SHIBA_COLLAR_TEX = new ResourceLocation(Doge.ID, "textures/entity/shiba/shiba_collar.png");
	
	public ShibaRender(EntityRendererManager renderMan) {
		super(renderMan);
		this.addLayer(new ShibaCollarLayer(this));
	}
	
	@Override
	public ResourceLocation getEntityTexture(WolfEntity wolf) {
		if (wolf.isTamed()) {
			return SHIBA_TAME_TEX;
		} else if (wolf.func_233678_J__()) {//isAngry
			return SHIBA_ANGRY_TEX;
		}
		return SHIBA_NORM_TEX;
	}
	
	public class ShibaCollarLayer extends WolfCollarLayer {
		
		public ShibaCollarLayer(ShibaRender shibaRender) {
			super(shibaRender);
		}
		
		@Override
		public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, WolfEntity wolf,
				float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			if (wolf.isTamed() && !wolf.isInvisible()) {
				float[] afloat = wolf.getCollarColor().getColorComponentValues();
				renderCutoutModel(this.getEntityModel(), SHIBA_COLLAR_TEX, matrixStack, buffer, packedLight, wolf, afloat[0], afloat[1], afloat[2]);
			}
		}
	}

	
}