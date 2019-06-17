package xiroc.beneficium.client.render.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.common.block.tileentity.TileEntityTreasureChest;

@SideOnly(Side.CLIENT)
public class TileEntityTreasureChestRenderer extends TileEntitySpecialRenderer<TileEntityTreasureChest> {

	private static final ResourceLocation TEXTURE_NORMAL = Beneficium.locate("textures/entity/chest/treasure.png");
	private final ModelChest model = new ModelChest();

	@Override
	public void render(TileEntityTreasureChest te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		int i;
		if (te.hasWorld()) {
			Block block = te.getBlockType();
			i = te.getBlockMetadata();
		} else {
			i = 0;
		}

		if (destroyStage >= 0) {
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		} else {
			this.bindTexture(TEXTURE_NORMAL);
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		if (destroyStage < 0) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		}

		GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		int j = 0;

		if (i == 2) {
			j = 180;
		}

		if (i == 3) {
			j = 0;
		}

		if (i == 4) {
			j = 90;
		}

		if (i == 5) {
			j = -90;
		}

		GlStateManager.rotate((float) j, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;

		f = 1.0F - f;
		f = 1.0F - f * f * f;
		model.chestLid.rotateAngleX = -(f * ((float) Math.PI / 2F));
		model.renderAll();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}

	}

}
