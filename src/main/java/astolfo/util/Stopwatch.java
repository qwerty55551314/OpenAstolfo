package astolfo.util;

import net.minecraft.client.Minecraft;

public final class Stopwatch {
   private long startTime = this.now();
   private static String[] o;

   public boolean hasElapsed(float f) {
      return (float)this.getElapsed() >= f;
   }

   public long getElapsed() {
      return this.now() - this.startTime;
   }

   public void reset() {
      this.startTime = this.now();
   }

   public long now() {
      return Minecraft.getSystemTime();
   }
}
