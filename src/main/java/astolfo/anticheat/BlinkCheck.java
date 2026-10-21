package astolfo.anticheat;

import astolfo.module.hidden.ServerRotationTracker;
import astolfo.util.Stopwatch;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class BlinkCheck extends MovementCheck {
   private double lastX;
   private double lastZ;
   private double lastY;
   private final Stopwatch moveTimer = new Stopwatch();
   private final List<Integer> moveIntervals = new ArrayList<>(10);

   public BlinkCheck() {
      super("Blink");
   }

   @Override
   public int handleMovement(double d, double e, double d2, float f, float g, boolean bl, EntityPlayer entityPlayer) {
      double d0 = Math.hypot(d - this.lastX, d2 - this.lastZ);
      double d1 = Math.abs(this.lastY - e);
      if (d0 <= 0.4) {
         this.moveTimer.reset();
         this.moveIntervals.clear();
      } else {
         double d21 = this.getAverage(this.moveIntervals);
         boolean flag = this.moveTimer.hasElapsed((float)(d21 + 500.0));
         if (flag && entityPlayer.isSprinting() && ServerRotationTracker.getTimeSinceTimeUpdate() < 1000L) {
            if (d0 > 5.0 && d0 < 50.0 && d1 < 5.0) {
               this.addViolations(entityPlayer.getName(), (int)(d0 / 5.0));
               this.moveTimer.reset();
            }

            this.moveIntervals.clear();
         }

         this.moveIntervals.add((int)this.moveTimer.getElapsed());
      }

      this.moveTimer.reset();
      this.lastX = d;
      this.lastZ = d2;
      this.lastY = e;
      return super.violations;
   }

   private double getAverage(List<Integer> list) {
      Integer integer = 0;
      if (list.isEmpty()) {
         return (double)integer.intValue();
      } else {
         for (Integer integer1 : list) {
            integer = integer + integer1;
         }

         return integer.doubleValue() / (double)list.size();
      }
   }

   @Override
   public int getMaxViolations() {
      return 10;
   }
}
