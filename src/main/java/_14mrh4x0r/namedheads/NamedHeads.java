package _14mrh4x0r.namedheads;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = NamedHeads.MODID, version = NamedHeads.VERSION)
@SideOnly(Side.CLIENT)
public class NamedHeads {
	public static final String MODID = "namedheads";
	public static final String VERSION = "0.2.2";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("[Named Heads] Registering on the event bus...");
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent(receiveCanceled = false)
	public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
		int x = event.target.blockX, y = event.target.blockY, z = event.target.blockZ;
		WorldClient world = Minecraft.getMinecraft().theWorld;
		if (world.getBlock(x, y, z) == Blocks.skull) {
			TileEntitySkull skull = (TileEntitySkull) world.getTileEntity(x, y, z);
			if (skull.func_145904_a() == 3) {
				AxisAlignedBB aabb = skull.getRenderBoundingBox();
				if (skull.func_152108_a() != null)
				{
					String s = skull.func_152108_a().getName();
					drawLabel(s, (aabb.maxX + aabb.minX) / 2 - event.player.posX, (aabb.maxY + aabb.minY) / 2 - event.player.posY - 0.3D, (aabb.maxZ + aabb.minZ) / 2 - event.player.posZ);
				}
			}
		}
	}
	
	private void drawLabel(String name, double offsetX, double offsetY, double offsetZ) {
		FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)offsetX, (float)offsetY + 1.0F, (float)offsetZ);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f1, -f1, f1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tessellator.startDrawingQuads();
        int j = fontrenderer.getStringWidth(name) / 2;
        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
        tessellator.addVertex((double)(-j - 1), (double)(-1), 0.0D);
        tessellator.addVertex((double)(-j - 1), (double)(8), 0.0D);
        tessellator.addVertex((double)(j + 1), (double)(8), 0.0D);
        tessellator.addVertex((double)(j + 1), (double)(-1), 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, 0, 553648127);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, 0, -1);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
	}
}