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
	
	public MiningRigTileEntity miningRig;
	
	public MiningRigGUI(InventoryPlayer invPlayer, MiningRigTileEntity miningRig) {
		super(new MiningRigContainer(invPlayer, miningRig));
		this.miningRig = miningRig;

		xSize = 193;
		ySize = 132;
	}
	
	private static final ResourceLocation texture = new ResourceLocation("doge", "textures/gui/mining_rig_gui.png");
	
	/**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		
		this.mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(miningRig.getName(), new Object[0]);
		int len = fontRenderer.getStringWidth(name);
		fontRenderer.drawString(name, (xSize - 18 - len) / 2, 7, 0x404040);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
	}
}
