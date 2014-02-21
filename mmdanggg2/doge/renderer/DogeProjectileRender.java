package mmdanggg2.doge.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

//@SideOnly(Side.CLIENT)
public class DogeProjectileRender extends RenderSnowball
{

	public DogeProjectileRender(Item par1Item, int par2) {
		super(par1Item, par2);
		// TODO Auto-generated constructor stub
	}
	
	public DogeProjectileRender(Item par1Item) {
		super(par1Item);
		// TODO Auto-generated constructor stub
	}
    
}
