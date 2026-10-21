package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIRestrictOpenDoor extends EntityAIBase {
   private EntityCreature entityObj;
   private VillageDoorInfo frontDoor;
   private static final String b = "Unsupported mob type for RestrictOpenDoorGoal";
   private static final long c = -8268501786482966512L;

   public EntityAIRestrictOpenDoor(EntityCreature creatureIn) {
      this.entityObj = creatureIn;
      if (!(creatureIn.getNavigator() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException(b);
      }
   }

   @Override
   public boolean shouldExecute() {
      if (this.entityObj.worldObj.isDaytime()) {
         return false;
      } else {
         BlockPos blockpos = new BlockPos(this.entityObj);
         Village village = this.entityObj.worldObj.getVillageCollection().getNearestVillage(blockpos, (int)c);
         if (village == null) {
            return false;
         } else {
            this.frontDoor = village.getNearestDoor(blockpos);
            return this.frontDoor == null ? false : (double)this.frontDoor.getDistanceToInsideBlockSq(blockpos) < 2.25;
         }
      }
   }

   @Override
   public boolean continueExecuting() {
      boolean flag = this.entityObj.worldObj.isDaytime();
      boolean flag3;
      if (flag) {
         flag3 = false;
      } else {
         boolean flag1 = this.frontDoor.getIsDetachedFromVillageFlag();
         if (!flag1) {
            boolean flag2 = this.frontDoor.func_179850_c(new BlockPos(this.entityObj));
            if (flag2) {
               return true;
            }
         }

         flag3 = false;
      }

      return flag3;
   }

   @Override
   public void startExecuting() {
      ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(false);
      ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(false);
   }

   @Override
   public void resetTask() {
      ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(true);
      ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(true);
      this.frontDoor = null;
   }

   @Override
   public void updateTask() {
      this.frontDoor.incrementDoorOpeningRestrictionCounter();
   }
}
