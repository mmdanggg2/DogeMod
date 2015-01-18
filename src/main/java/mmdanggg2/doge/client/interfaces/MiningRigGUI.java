package mmdanggg2.doge.client.interfaces;

import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MiningRigGUI extends GuiContainer {
	
	public MiningRigGUI(InventoryPlayer invPlayer, MiningRigTileEntity miningRig) {
		super(new MiningRigContainer(invPlayer, miningRig));

		xSize = 193;
		ySize = 132;
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
		String name = I18n.format("tile.miningRig.name", new Object[0]);
		int len = fontRendererObj.getStringWidth(name);
		fontRendererObj.drawString(name, (xSize - 18 - len) / 2, 7, 0x404040);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
	}
}
