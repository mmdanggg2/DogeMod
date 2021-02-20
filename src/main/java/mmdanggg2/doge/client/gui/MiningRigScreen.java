package mmdanggg2.doge.client.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mmdanggg2.doge.container.MiningRigContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MiningRigScreen extends ContainerScreen<MiningRigContainer> {
	
	public MiningRigScreen(MiningRigContainer miningRig, PlayerInventory invPlayer, ITextComponent title) {
		super(miningRig, invPlayer, title);
		
		xSize = 193;
		ySize = 132;
	}
	
	private static final ResourceLocation texture = new ResourceLocation("doge", "textures/gui/mining_rig_gui.png");
	
	/**
     * Draws the screen and all the components in it.
     */
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1, 1, 1, 1);
		
		this.minecraft.getTextureManager().bindTexture(texture);
		blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		String name = title.getString();
		int len = font.getStringWidth(name);
		font.drawString(matrixStack, name, (xSize - 18 - len) / 2, 7, 0x404040);
	}
}
