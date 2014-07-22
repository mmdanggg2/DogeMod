package mmdanggg2.doge.client.interfaces;

import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MiningRigGUI extends GuiContainer {
	
	public MiningRigGUI(InventoryPlayer invPlayer, MiningRigTileEntity miningRig) {
		super(new MiningRigContainer(invPlayer, miningRig));

		xSize = 176;
		ySize = 154;
	}
	
	private static final ResourceLocation texture = new ResourceLocation("doge", "textures/gui/miningRigGUI.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0, int arg1, int arg2) {
		GL11.glColor4f(1, 1, 1, 1);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		fontRendererObj.drawString("Mining Rig", 8, 2, 0x404040);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
	}
}
