package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityAIBreakDoor extends EntityAIDoorInteract {
   private int breakingTime;
   private int previousBreakProgress = -1;
   private static final String e = "mobGriefing";

   public EntityAIBreakDoor(EntityLiving entityIn) {
      super(entityIn);
   }

   @Override
   public boolean shouldExecute() {
      boolean flag = super.shouldExecute();
      if (!flag) {
         return false;
      } else if (!super.theEntity.worldObj.getGameRules().getBoolean(e)) {
         return false;
      } else {
         BlockDoor blockdoor = super.doorBlock;
         boolean flag1 = BlockDoor.isOpen(super.theEntity.worldObj, super.doorPosition);
         return !flag1;
      }
   }

   @Override
   public void startExecuting() {
      super.startExecuting();
      this.breakingTime = 0;
   }

   @Override
   public boolean continueExecuting() {
      double d0 = super.theEntity.getDistanceSq(super.doorPosition);
      if (this.breakingTime <= 240) {
         BlockDoor blockdoor = super.doorBlock;
         if (!BlockDoor.isOpen(super.theEntity.worldObj, super.doorPosition) && d0 < 4.0) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void resetTask() {
      super.resetTask();
      super.theEntity.worldObj.sendBlockBreakProgress(super.theEntity.getEntityId(), super.doorPosition, -1);
   }

   @Override
   public void updateTask() {
      super.updateTask();
      int j = super.theEntity.getRNG().nextInt(20);
      if (j == 0) {
         super.theEntity.worldObj.playAuxSFX(1010, super.doorPosition, 0);
      }

      this.breakingTime++;
      int i = (int)((float)this.breakingTime / 240.0F * 10.0F);
      int k = this.previousBreakProgress;
      if (i != k) {
         super.theEntity.worldObj.sendBlockBreakProgress(super.theEntity.getEntityId(), super.doorPosition, i);
         this.previousBreakProgress = i;
      }

      if (this.breakingTime == 240) {
         World world = super.theEntity.worldObj;
         if (world.getDifficulty() == EnumDifficulty.HARD) {
            super.theEntity.worldObj.setBlockToAir(super.doorPosition);
            super.theEntity.worldObj.playAuxSFX(1012, super.doorPosition, 0);
            super.theEntity.worldObj.playAuxSFX(2001, super.doorPosition, Block.getIdFromBlock(super.doorBlock));
         }
      }
   }
}
