package net.minecraft.client.renderer.tileentity;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer<TileEntityChest> {
   private static final ResourceLocation textureTrappedDouble = new ResourceLocation("textures/entity/chest/trapped_double.png");
   private static final ResourceLocation textureChristmasDouble = new ResourceLocation("textures/entity/chest/christmas_double.png");
   private static final ResourceLocation textureNormalDouble = new ResourceLocation("textures/entity/chest/normal_double.png");
   private static final ResourceLocation textureTrapped = new ResourceLocation("textures/entity/chest/trapped.png");
   private static final ResourceLocation textureChristmas = new ResourceLocation("textures/entity/chest/christmas.png");
   private static final ResourceLocation textureNormal = new ResourceLocation("textures/entity/chest/normal.png");
   private ModelChest simpleChest = new ModelChest();
   private ModelChest largeChest = new ModelLargeChest();
   private boolean isChristmas;

   public TileEntityChestRenderer() {
      Calendar calendar = Calendar.getInstance();
      if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
         this.isChristmas = true;
      }
   }

   public void renderTileEntityAt(TileEntityChest te, double x, double y, double z, float partialTicks, int destroyStage) {
      String[] astring = new String[0];
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      int i;
      if (!te.hasWorldObj()) {
         i = 0;
      } else {
         Block block = te.getBlockType();
         i = te.getBlockMetadata();
         boolean flag = block instanceof BlockChest;
         if (flag && i == 0) {
            ((BlockChest)block).checkForSurroundingChests(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()));
            i = te.getBlockMetadata();
         }

         te.checkForAdjacentChests();
      }

      TileEntityChest tileentitychest = te.adjacentChestZNeg;
      if (tileentitychest == null) {
         TileEntityChest tileentitychest1 = te.adjacentChestXNeg;
         if (tileentitychest1 == null) {
            TileEntityChest tileentitychest2 = te.adjacentChestXPos;
            ModelChest modelchest;
            if (tileentitychest2 == null && te.adjacentChestZPos == null) {
               modelchest = this.simpleChest;
               if (destroyStage >= 0) {
                  this.bindTexture(TileEntitySpecialRenderer.DESTROY_STAGES[destroyStage]);
                  GlStateManager.matrixMode(5890);
                  GlStateManager.pushMatrix();
                  GlStateManager.scale(4.0F, 4.0F, 1.0F);
                  GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                  GlStateManager.matrixMode(5888);
               } else {
                  int j = te.getChestType();
                  if (j == 1) {
                     this.bindTexture(textureTrapped);
                  } else if (this.isChristmas) {
                     this.bindTexture(textureChristmas);
                  } else {
                     this.bindTexture(textureNormal);
                  }
               }
            } else {
               modelchest = this.largeChest;
               if (destroyStage >= 0) {
                  this.bindTexture(TileEntitySpecialRenderer.DESTROY_STAGES[destroyStage]);
                  GlStateManager.matrixMode(5890);
                  GlStateManager.pushMatrix();
                  GlStateManager.scale(8.0F, 4.0F, 1.0F);
                  GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                  GlStateManager.matrixMode(5888);
               } else {
                  int k = te.getChestType();
                  if (k == 1) {
                     this.bindTexture(textureTrappedDouble);
                  } else if (this.isChristmas) {
                     this.bindTexture(textureChristmasDouble);
                  } else {
                     this.bindTexture(textureNormalDouble);
                  }
               }
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            if (destroyStage < 0) {
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            GlStateManager.translate(0.5F, 0.5F, 0.5F);
            int short1 = 0;
            if (i == 2) {
               short1 = 180;
            }

            if (i == 3) {
               short1 = 0;
            }

            if (i == 4) {
               short1 = 90;
            }

            if (i == 5) {
               short1 = -90;
            }

            if (i == 2 && te.adjacentChestXPos != null) {
               GlStateManager.translate(1.0F, 0.0F, 0.0F);
            }

            if (i == 5) {
               TileEntityChest tileentitychest3 = te.adjacentChestZPos;
               if (tileentitychest3 != null) {
                  GlStateManager.translate(0.0F, 0.0F, -1.0F);
               }
            }

            GlStateManager.rotate((float)short1, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
            TileEntityChest tileentitychest4 = te.adjacentChestZNeg;
            if (tileentitychest4 != null) {
               float f1 = te.adjacentChestZNeg.prevLidAngle + (te.adjacentChestZNeg.lidAngle - te.adjacentChestZNeg.prevLidAngle) * partialTicks;
               if (f1 > f) {
                  f = f1;
               }
            }

            TileEntityChest tileentitychest5 = te.adjacentChestXNeg;
            if (tileentitychest5 != null) {
               float f2 = te.adjacentChestXNeg.prevLidAngle + (te.adjacentChestXNeg.lidAngle - te.adjacentChestXNeg.prevLidAngle) * partialTicks;
               float f3;
               int l = (f3 = f2 - f) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
               if (l > 0) {
                  f = f2;
               }
            }

            f = 1.0F - f;
            f = 1.0F - f * f * f;
            modelchest.chestLid.rotateAngleX = -(f * (float) Math.PI / 2.0F);
            modelchest.renderAll();
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
   }
}
