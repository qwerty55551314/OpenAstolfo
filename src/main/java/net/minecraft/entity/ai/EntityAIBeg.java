package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAIBeg extends EntityAIBase {
   private EntityWolf theWolf;
   private EntityPlayer thePlayer;
   private World worldObject;
   private float minPlayerDistance;
   private int timeoutCounter;

   public EntityAIBeg(EntityWolf wolf, float minDistance) {
      this.theWolf = wolf;
      this.worldObject = wolf.worldObj;
      this.minPlayerDistance = minDistance;
      this.setMutexBits(2);
   }

   @Override
   public boolean shouldExecute() {
      this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, (double)this.minPlayerDistance);
      return this.thePlayer == null ? false : this.hasPlayerGotBoneInHand(this.thePlayer);
   }

   @Override
   public boolean continueExecuting() {
      boolean flag = this.thePlayer.isEntityAlive();
      boolean flag2;
      if (!flag) {
         flag2 = false;
      } else {
         double d0;
         int i = (d0 = this.theWolf.getDistanceSqToEntity(this.thePlayer) - (double)(this.minPlayerDistance * this.minPlayerDistance)) == 0.0
            ? 0
            : (d0 < 0.0 ? -1 : 1);
         if (i > 0) {
            flag2 = false;
         } else {
            int j = this.timeoutCounter;
            if (j > 0) {
               boolean flag1 = this.hasPlayerGotBoneInHand(this.thePlayer);
               if (flag1) {
                  return true;
               }
            }

            flag2 = false;
         }
      }

      return flag2;
   }

   @Override
   public void startExecuting() {
      this.theWolf.setBegging(true);
      this.timeoutCounter = 40 + this.theWolf.getRNG().nextInt(40);
   }

   @Override
   public void resetTask() {
      this.theWolf.setBegging(false);
      this.thePlayer = null;
   }

   @Override
   public void updateTask() {
      this.theWolf
         .getLookHelper()
         .setLookPosition(
            this.thePlayer.posX,
            this.thePlayer.posY + (double)this.thePlayer.getEyeHeight(),
            this.thePlayer.posZ,
            10.0F,
            (float)this.theWolf.getVerticalFaceSpeed()
         );
      this.timeoutCounter--;
   }

   private boolean hasPlayerGotBoneInHand(EntityPlayer player) {
      ItemStack itemstack = player.inventory.getCurrentItem();
      return itemstack == null ? false : (!this.theWolf.isTamed() && itemstack.getItem() == Items.bone ? true : this.theWolf.isBreedingItem(itemstack));
   }
}
