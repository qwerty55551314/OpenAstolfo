package astolfo.anticheat;

import net.minecraft.entity.player.EntityPlayer;

public abstract class MovementCheck extends AbstractCheck {
   public MovementCheck(String string) {
      super(string, CheckType.MOVEMENT);
   }

   public abstract int handleMovement(double var1, double var3, double var5, float var7, float var8, boolean var9, EntityPlayer var10);
}
