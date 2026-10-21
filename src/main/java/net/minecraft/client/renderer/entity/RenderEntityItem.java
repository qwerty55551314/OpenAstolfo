package net.minecraft.client.renderer.entity;

import astolfo.module.ModuleManager;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderEntityItem extends Render<EntityItem> {
   private final RenderItem itemRenderer;
   private final Random B = new Random();
   public static long lastFrameNanoTime;
   public static double physicsRotationDelta;

   public RenderEntityItem(RenderManager renderManagerIn, RenderItem renderItem) {
      super(renderManagerIn);
      this.itemRenderer = renderItem;
      super.shadowSize = 0.15F;
      super.shadowOpaque = 0.75F;
   }

   private int e(EntityItem itemIn, double d, double e, double d2, float f, IBakedModel iBakedModel) {
      ItemStack itemstack = itemIn.getEntityItem();
      Item item = itemstack.getItem();
      if (item == null) {
         return 0;
      } else {
         boolean flag = iBakedModel.isGui3d();
         int i = this.p(itemstack);
         float f1 = MathHelper.sin(((float)itemIn.getAge() + f) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
         float f1x = iBakedModel.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
         GlStateManager.translate((float)d, (float)e + f1 + 0.25F * f1x, (float)d2);
         if (flag || super.renderManager.options != null) {
            float f2 = (((float)itemIn.getAge() + f) / 20.0F + itemIn.hoverStart) * (180.0F / (float)Math.PI);
            GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
         }

         if (!flag) {
            float f5 = -0.0F * (float)(i - 1) * 0.5F;
            float f3 = -0.0F * (float)(i - 1) * 0.5F;
            float f4 = -0.046875F * (float)(i - 1) * 0.5F;
            GlStateManager.translate(f5, f3, f4);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         return i;
      }
   }

   private int p(ItemStack stack) {
      int b0 = 1;
      int i = stack.stackSize;
      if (i > 48) {
         b0 = 5;
      } else {
         int j = stack.stackSize;
         if (j > 32) {
            b0 = 4;
         } else {
            int k = stack.stackSize;
            if (k > 16) {
               b0 = 3;
            } else if (stack.stackSize > 1) {
               b0 = 2;
            }
         }
      }

      return b0;
   }

   public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (ModuleManager.itemPhysics.isEnabled()) {
         this.renderItemPhysics(entity, x, y, z, entityYaw, partialTicks);
      } else {
         ItemStack itemstack = entity.getEntityItem();
         this.B.setSeed(187L);
         boolean flag = false;
         if (this.bindEntityTexture(entity)) {
            super.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
            flag = true;
         }

         GlStateManager.enableRescaleNormal();
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.pushMatrix();
         IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
         int i = this.e(entity, x, y, z, partialTicks, ibakedmodel);

         for (int j = 0; j < i; j++) {
            if (ibakedmodel.isGui3d()) {
               GlStateManager.pushMatrix();
               if (j > 0) {
                  float f = (this.B.nextFloat() * 2.0F - 1.0F) * 0.15F;
                  float f1 = (this.B.nextFloat() * 2.0F - 1.0F) * 0.15F;
                  float f2 = (this.B.nextFloat() * 2.0F - 1.0F) * 0.15F;
                  GlStateManager.translate(f, f1, f2);
               }

               GlStateManager.scale(0.5F, 0.5F, 0.5F);
               ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
               this.itemRenderer.renderItem(itemstack, ibakedmodel);
               GlStateManager.popMatrix();
            } else {
               GlStateManager.pushMatrix();
               ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
               this.itemRenderer.renderItem(itemstack, ibakedmodel);
               GlStateManager.popMatrix();
               float f3 = ibakedmodel.getItemCameraTransforms().ground.scale.x;
               float f4 = ibakedmodel.getItemCameraTransforms().ground.scale.y;
               float f5 = ibakedmodel.getItemCameraTransforms().ground.scale.z;
               GlStateManager.translate(0.0F * f3, 0.0F * f4, 0.046875F * f5);
            }
         }

         GlStateManager.popMatrix();
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableBlend();
         this.bindEntityTexture(entity);
         if (flag) {
            super.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
         }

         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }
   }

   private void renderItemPhysics(EntityItem entityItem, double d, double e, double d2, float f, float g) {
      double d1 = (double)(System.nanoTime() - lastFrameNanoTime) / 2500000.0;
      physicsRotationDelta = d1;
      if (!Minecraft.getMinecraft().inGameHasFocus) {
         physicsRotationDelta = 0.0;
      }

      ResourceLocation resourcelocation;
      ItemStack itemstack;
      int i;
      label74: {
         resourcelocation = this.getEntityTexture(entityItem);
         itemstack = entityItem.getEntityItem();
         if (itemstack != null) {
            Item item = itemstack.getItem();
            if (item != null) {
               i = Item.getIdFromItem(itemstack.getItem()) + itemstack.getMetadata();
               break label74;
            }
         }

         i = 187;
      }

      this.B.setSeed((long)i);
      this.bindTexture(resourcelocation);
      this.getRenderManager().renderEngine.getTexture(resourcelocation).setBlurMipmap(false, false);
      GlStateManager.enableRescaleNormal();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableBlend();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.pushMatrix();
      IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
      boolean flag = ibakedmodel.isGui3d();
      boolean flag1 = ibakedmodel.isGui3d();
      int j = this.p(itemstack);
      float f1 = ibakedmodel.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
      GlStateManager.translate((float)d, (float)e, (float)d2);
      boolean flag2 = ibakedmodel.isGui3d();
      if (flag2) {
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
      }

      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(entityItem.rotationYaw, 0.0F, 0.0F, 1.0F);
      if (flag1) {
         GlStateManager.translate(0.0, 0.0, -0.08);
      } else {
         GlStateManager.translate(0.0, 0.0, -0.04);
      }

      if (flag1 || this.getRenderManager().options != null) {
         if (flag1) {
            if (!entityItem.onGround) {
               double d0 = physicsRotationDelta * 2.0;
               float f2 = getSubmergedFlowDecay(entityItem);
               if (f2 != -1.0F) {
                  d0 /= (double)(f2 / 1000.0F * 10.0F);
               }

               entityItem.rotationPitch = (float)((double)entityItem.rotationPitch + d0);
            }
         } else if (!Double.isNaN(entityItem.posX) && !Double.isNaN(entityItem.posY) && !Double.isNaN(entityItem.posZ) && entityItem.worldObj != null) {
            if (entityItem.onGround) {
               entityItem.rotationPitch = 0.0F;
            } else {
               double d21 = physicsRotationDelta * 2.0;
               float f3 = getSubmergedFlowDecay(entityItem);
               if (f3 != -1.0F) {
                  d21 /= (double)(f3 / 1000.0F * 10.0F);
               }

               entityItem.rotationPitch = (float)((double)entityItem.rotationPitch + d21);
            }
         }

         GlStateManager.rotate(entityItem.rotationPitch, 1.0F, 0.0F, 0.0F);
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

      for (int k = 0; k < j; k++) {
         GlStateManager.pushMatrix();
         if (flag) {
            if (k > 0) {
               float f1x = (this.B.nextFloat() * 2.0F - 1.0F) * 0.15F;
               GlStateManager.translate(0.0F, 0.0F, f1x);
            }

            ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
            this.itemRenderer.renderItem(itemstack, ibakedmodel);
            GlStateManager.popMatrix();
         } else {
            ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
            this.itemRenderer.renderItem(itemstack, ibakedmodel);
            GlStateManager.popMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.05375F);
         }
      }

      GlStateManager.popMatrix();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      this.bindTexture(resourcelocation);
      this.getRenderManager().renderEngine.getTexture(resourcelocation).restoreLastBlurMipmap();
   }

   public static float getSubmergedFlowDecay(EntityItem entityItem) {
      return getLiquidFlowDecayAt(entityItem, false);
   }

   public static float getLiquidFlowDecayAt(EntityItem entityItem, boolean bl) {
      double d0 = entityItem.posY + (double)entityItem.getEyeHeight();
      int i = MathHelper.floor_double(entityItem.posX);
      int j = MathHelper.floor_float((float)MathHelper.floor_double(d0));
      if (bl) {
         j--;
      }

      int k = MathHelper.floor_double(entityItem.posZ);
      BlockPos blockpos = new BlockPos(i, j, k);
      Block block = entityItem.worldObj.getBlockState(blockpos).getBlock();
      float f = -1.0F;
      boolean flag = block instanceof BlockLiquid;
      if (flag) {
         BlockLiquid blockliquid = (BlockLiquid)block;
         f = (float)blockliquid.getEffectiveFlowDecay(entityItem.worldObj, blockpos);
      }

      if (bl) {
         return f;
      } else {
         double d1 = 1.0;
         boolean flag1 = block instanceof BlockLiquid;
         if (flag1) {
            d1 = (double)((BlockLiquid)block).getLevel(entityItem.worldObj, blockpos);
         }

         double d2;
         int l = (d2 = d1 - 0.0) == 0.0 ? 0 : (d2 < 0.0 ? -1 : 1);
         if (l < 0) {
            d1 *= -1.0;
            if (d0 > (double)j + (1.0 - d1)) {
               return f;
            }
         } else if (d0 < (double)j + d1) {
            return f;
         }

         return -1.0F;
      }
   }

   public ResourceLocation getEntityTexture(EntityItem entity) {
      return TextureMap.locationBlocksTexture;
   }
}
