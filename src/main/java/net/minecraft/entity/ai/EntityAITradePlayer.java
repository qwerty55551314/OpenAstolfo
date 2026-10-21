package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class EntityAITradePlayer extends EntityAIBase {
   private EntityVillager villager;

   public EntityAITradePlayer(EntityVillager villagerIn) {
      this.villager = villagerIn;
      this.setMutexBits(5);
   }

   @Override
   public boolean shouldExecute() {
      boolean flag = this.villager.isEntityAlive();
      if (!flag) {
         return false;
      } else {
         boolean flag1 = this.villager.isInWater();
         if (flag1) {
            return false;
         } else {
            boolean flag2 = this.villager.onGround;
            if (!flag2) {
               return false;
            } else if (this.villager.velocityChanged) {
               return false;
            } else {
               EntityPlayer entityplayer = this.villager.getCustomer();
               boolean flag3;
               if (entityplayer == null) {
                  flag3 = false;
               } else {
                  double d0;
                  int i = (d0 = this.villager.getDistanceSqToEntity(entityplayer) - 16.0) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
                  flag3 = i > 0 ? false : entityplayer.openContainer instanceof Container;
               }

               return flag3;
            }
         }
      }
   }

   @Override
   public void startExecuting() {
      this.villager.getNavigator().clearPathEntity();
   }

   @Override
   public void resetTask() {
      this.villager.setCustomer((EntityPlayer)null);
   }
}
