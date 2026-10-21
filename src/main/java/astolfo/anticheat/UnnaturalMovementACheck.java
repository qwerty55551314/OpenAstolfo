package astolfo.anticheat;

import astolfo.ClientChat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class UnnaturalMovementACheck extends MovementCheck {
   private final List<Double> distanceHistory = new ArrayList<>(25);
   private double lastX;
   private double lastZ;
   private double lastDistance;

   public UnnaturalMovementACheck() {
      super("Unnatural Movement A");
   }

   @Override
   public int handleMovement(double d, double e, double d2, float f, float g, boolean bl, EntityPlayer entityPlayer) {
      double d0 = Math.hypot(d - this.lastX, d2 - this.lastZ);
      double d1 = Math.abs(d0) - Math.abs(this.lastDistance);
      if (d1 != 0.0 && d1 > Math.abs(this.lastDistance) * 2.0) {
         ClientChat.CHAT.print("lol detect");
         super.violations++;
      }

      this.lastX = d;
      this.lastZ = d2;
      this.lastDistance = d0;
      return super.violations;
   }

   @Override
   public int getMaxViolations() {
      return 3;
   }

   private double U(List<Double> list) {
      Double d0 = 0.0;
      if (list.isEmpty()) {
         return d0;
      } else {
         for (Double d1 : list) {
            Double d2 = d0 + d1;
            d0 = d2;
         }

         return d0 / (double)list.size();
      }
   }
}
