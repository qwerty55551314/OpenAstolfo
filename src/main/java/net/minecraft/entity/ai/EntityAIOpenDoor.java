package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenDoor extends EntityAIDoorInteract {
   public boolean closeDoor;
   public int closeDoorTemporisation;
   private static final long e = -8221712812192301036L;

   public EntityAIOpenDoor(EntityLiving entitylivingIn, boolean shouldClose) {
      super(entitylivingIn);
      super.theEntity = entitylivingIn;
      this.closeDoor = shouldClose;
   }

   @Override
   public boolean continueExecuting() {
      boolean flag = this.closeDoor;
      if (flag) {
         int i = this.closeDoorTemporisation;
         if (i > 0) {
            boolean flag1 = super.continueExecuting();
            if (flag1) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void startExecuting() {
      this.closeDoorTemporisation = (int)e;
      super.doorBlock.toggleDoor(super.theEntity.worldObj, super.doorPosition, true);
   }

   @Override
   public void resetTask() {
      if (this.closeDoor) {
         super.doorBlock.toggleDoor(super.theEntity.worldObj, super.doorPosition, false);
      }
   }

   @Override
   public void updateTask() {
      this.closeDoorTemporisation--;
      super.updateTask();
   }
}
