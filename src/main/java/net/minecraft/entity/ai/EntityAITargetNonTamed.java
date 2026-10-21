package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAITargetNonTamed<T extends EntityLivingBase> extends EntityAINearestAttackableTarget {
   private EntityTameable theTameable;
   private static final long e = 3816365406440914954L;

   public EntityAITargetNonTamed(EntityTameable entityIn, Class<T> classTarget, boolean checkSight, Predicate<? super T> targetSelector) {
      super(entityIn, classTarget, (int)e, checkSight, false, targetSelector);
      this.theTameable = entityIn;
   }

   @Override
   public boolean shouldExecute() {
      boolean flag = this.theTameable.isTamed();
      if (!flag) {
         boolean flag1 = super.shouldExecute();
         if (flag1) {
            return true;
         }
      }

      return false;
   }
}
