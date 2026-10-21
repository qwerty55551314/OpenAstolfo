package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntitySenses {
   public EntityLiving entityObj;
   public List<Entity> seenEntities = Lists.newArrayList();
   public List<Entity> unseenEntities = Lists.newArrayList();
   private static final String b = "canSee";

   public EntitySenses(EntityLiving entityObjIn) {
      this.entityObj = entityObjIn;
   }

   public void clearSensingCache() {
      this.seenEntities.clear();
      this.unseenEntities.clear();
   }

   public boolean canSee(Entity entityIn) {
      boolean flag1 = this.seenEntities.contains(entityIn);
      if (flag1) {
         return true;
      } else {
         boolean flag2 = this.unseenEntities.contains(entityIn);
         if (flag2) {
            return false;
         } else {
            this.entityObj.worldObj.theProfiler.startSection(b);
            boolean flag = this.entityObj.canEntityBeSeen(entityIn);
            this.entityObj.worldObj.theProfiler.endSection();
            if (flag) {
               this.seenEntities.add(entityIn);
            } else {
               this.unseenEntities.add(entityIn);
            }

            return flag;
         }
      }
   }
}
