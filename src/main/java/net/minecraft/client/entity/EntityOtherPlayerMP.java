package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityOtherPlayerMP extends AbstractClientPlayer {
   private boolean isItemInUse;
   private int otherPlayerMPPosRotationIncrements;
   private double otherPlayerMPX;
   private double otherPlayerMPY;
   private double otherPlayerMPZ;
   private double otherPlayerMPYaw;
   private double otherPlayerMPPitch;

   public EntityOtherPlayerMP(World worldIn, GameProfile gameProfileIn) {
      super(worldIn, gameProfileIn);
      super.stepHeight = 0.0F;
      super.noClip = true;
      super.renderOffsetY = 0.25F;
      super.renderDistanceWeight = 10.0;
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      return true;
   }

   @Override
   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean bl) {
      this.otherPlayerMPX = x;
      this.otherPlayerMPY = y;
      this.otherPlayerMPZ = z;
      this.otherPlayerMPYaw = (double)yaw;
      this.otherPlayerMPPitch = (double)pitch;
      this.otherPlayerMPPosRotationIncrements = posRotationIncrements;
   }

   @Override
   public void onUpdate() {
      super.renderOffsetY = 0.0F;
      super.onUpdate();
      super.prevLimbSwingAmount = super.limbSwingAmount;
      double d0 = super.posX - super.prevPosX;
      double d1 = super.posZ - super.prevPosZ;
      float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;
      float f1;
      int i = (f1 = f - 1.0F) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
      if (i > 0) {
         f = 1.0F;
      }

      super.limbSwingAmount = super.limbSwingAmount + (f - super.limbSwingAmount) * 0.4F;
      super.limbSwing = super.limbSwing + super.limbSwingAmount;
      boolean flag = this.isItemInUse;
      if (!flag && this.isEating() && super.inventory.mainInventory[super.inventory.currentItem] != null) {
         ItemStack itemstack = super.inventory.mainInventory[super.inventory.currentItem];
         this.setItemInUse(super.inventory.mainInventory[super.inventory.currentItem], itemstack.getItem().getMaxItemUseDuration(itemstack));
         this.isItemInUse = true;
      } else {
         boolean flag1 = this.isItemInUse;
         if (flag1 && !this.isEating()) {
            this.clearItemInUse();
            this.isItemInUse = false;
         }
      }
   }

   @Override
   public void onLivingUpdate() {
      if (this.otherPlayerMPPosRotationIncrements > 0) {
         double d0 = super.posX + (this.otherPlayerMPX - super.posX) / (double)this.otherPlayerMPPosRotationIncrements;
         double d1 = super.posY + (this.otherPlayerMPY - super.posY) / (double)this.otherPlayerMPPosRotationIncrements;
         double d2 = super.posZ + (this.otherPlayerMPZ - super.posZ) / (double)this.otherPlayerMPPosRotationIncrements;
         double d3 = this.otherPlayerMPYaw - (double)super.rotationYaw;

         while (d3 < -180.0) {
            d3 += 360.0;
         }

         while (d3 >= 180.0) {
            d3 -= 360.0;
         }

         super.rotationYaw = (float)((double)super.rotationYaw + d3 / (double)this.otherPlayerMPPosRotationIncrements);
         super.rotationPitch = (float)(
            (double)super.rotationPitch + (this.otherPlayerMPPitch - (double)super.rotationPitch) / (double)this.otherPlayerMPPosRotationIncrements
         );
         this.otherPlayerMPPosRotationIncrements--;
         this.setPosition(d0, d1, d2);
         this.setRotation(super.rotationYaw, super.rotationPitch);
      }

      super.prevCameraYaw = super.cameraYaw;
      this.updateArmSwingProgress();
      float f1 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
      float f = (float)Math.atan(-super.motionY * 0.2F) * 15.0F;
      float f2;
      int i = (f2 = f1 - 0.1F) == 0.0F ? 0 : (f2 < 0.0F ? -1 : 1);
      if (i > 0) {
         f1 = 0.1F;
      }

      label29: {
         boolean flag = super.onGround;
         if (flag) {
            float f3;
            int j = (f3 = this.getHealth() - 0.0F) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
            if (j > 0) {
               break label29;
            }
         }

         f1 = 0.0F;
      }

      boolean flag1 = super.onGround;
      if (flag1 || this.getHealth() <= 0.0F) {
         f = 0.0F;
      }

      super.cameraYaw = super.cameraYaw + (f1 - super.cameraYaw) * 0.4F;
      super.cameraPitch = super.cameraPitch + (f - super.cameraPitch) * 0.8F;
   }

   @Override
   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
      if (slotIn == 0) {
         super.inventory.mainInventory[super.inventory.currentItem] = stack;
      } else {
         super.inventory.armorInventory[slotIn - 1] = stack;
      }
   }

   @Override
   public void addChatMessage(IChatComponent component) {
      Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(component);
   }

   @Override
   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
      return false;
   }

   @Override
   public BlockPos getPosition() {
      return new BlockPos(super.posX + 0.5, super.posY + 0.5, super.posZ + 0.5);
   }
}
