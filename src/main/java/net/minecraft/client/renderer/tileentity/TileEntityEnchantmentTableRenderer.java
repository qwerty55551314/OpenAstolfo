package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileEntityEnchantmentTableRenderer extends TileEntitySpecialRenderer<TileEntityEnchantmentTable> {
   private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private ModelBook field_147541_c = new ModelBook();

   public void renderTileEntityAt(TileEntityEnchantmentTable te, double x, double y, double z, float partialTicks, int destroyStage) {
      String[] astring = new String[0];
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F, (float)z + 0.5F);
      float f = (float)te.tickCount + partialTicks;
      GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);
      float f1 = te.bookRotation - te.bookRotationPrev;

      while (f1 >= (float) Math.PI) {
         f1 -= (float) (Math.PI * 2);
      }

      while (f1 < (float) -Math.PI) {
         float f6 = f1 + (float) (Math.PI * 2);
         f1 = f6;
      }

      float f2 = te.bookRotationPrev + f1 * partialTicks;
      GlStateManager.rotate(-f2 * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
      this.bindTexture(TEXTURE_BOOK);
      float f3 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25F;
      float f4 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75F;
      f3 = (f3 - (float)MathHelper.truncateDoubleToInt((double)f3)) * 1.6F - 0.3F;
      f4 = (f4 - (float)MathHelper.truncateDoubleToInt((double)f4)) * 1.6F - 0.3F;
      float f7;
      int i = (f7 = f3 - 0.0F) == 0.0F ? 0 : (f7 < 0.0F ? -1 : 1);
      if (i < 0) {
         f3 = 0.0F;
      }

      float f8;
      int j = (f8 = f4 - 0.0F) == 0.0F ? 0 : (f8 < 0.0F ? -1 : 1);
      if (j < 0) {
         f4 = 0.0F;
      }

      float f9;
      int k = (f9 = f3 - 1.0F) == 0.0F ? 0 : (f9 < 0.0F ? -1 : 1);
      if (k > 0) {
         f3 = 1.0F;
      }

      if (f4 > 1.0F) {
         f4 = 1.0F;
      }

      float f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;
      GlStateManager.enableCull();
      this.field_147541_c.render((Entity)null, f, f3, f4, f5, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
   }
}
