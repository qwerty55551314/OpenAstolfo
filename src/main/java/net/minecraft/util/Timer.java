package net.minecraft.util;

import astolfo.util.Stopwatch;
import net.minecraft.client.Minecraft;

public class Timer {
   public float ticksPerSecond;
   private double lastHRTime;
   public int elapsedTicks;
   public float renderPartialTicks;
   public float timerSpeed = 1.0F;
   public float elapsedPartialTicks;
   private long lastSyncSysClock;
   private long lastSyncHRClock;
   private long counter;
   private double timeSyncAdjustment = 1.0;
   public static Stopwatch authPingTimer = new Stopwatch();

   public Timer(float tps) {
      this.ticksPerSecond = tps;
      this.lastSyncSysClock = Minecraft.getSystemTime();
      this.lastSyncHRClock = System.nanoTime() / 1000000L;
   }

   public void updateTimer() {
      long i;
      double d0;
      label22: {
         i = Minecraft.getSystemTime();
         long j = i - this.lastSyncSysClock;
         long k = System.nanoTime() / 1000000L;
         d0 = (double)k / 1000.0;
         long l1;
         int i1 = (l1 = j - 1000L) == 0L ? 0 : (l1 < 0L ? -1 : 1);
         if (i1 <= 0) {
            long i2;
            int j1 = (i2 = j - 0L) == 0L ? 0 : (i2 < 0L ? -1 : 1);
            if (j1 >= 0) {
               this.counter += j;
               long j2;
               int k1 = (j2 = this.counter - 1000L) == 0L ? 0 : (j2 < 0L ? -1 : 1);
               if (k1 > 0) {
                  long l = k - this.lastSyncHRClock;
                  double d1 = (double)this.counter / (double)l;
                  this.timeSyncAdjustment = this.timeSyncAdjustment + (d1 - this.timeSyncAdjustment) * 0.2F;
                  this.lastSyncHRClock = k;
                  this.counter = 0L;
               }

               if (this.counter < 0L) {
                  this.lastSyncHRClock = k;
               }
               break label22;
            }
         }

         this.lastHRTime = d0;
      }

      this.lastSyncSysClock = i;
      double d2 = (d0 - this.lastHRTime) * this.timeSyncAdjustment;
      this.lastHRTime = d0;
      d2 = MathHelper.clamp_double(d2, 0.0, 1.0);
      this.elapsedPartialTicks = (float)((double)this.elapsedPartialTicks + d2 * (double)this.timerSpeed * (double)this.ticksPerSecond);
      this.elapsedTicks = (int)this.elapsedPartialTicks;
      this.elapsedPartialTicks = this.elapsedPartialTicks - (float)this.elapsedTicks;
      if (this.elapsedTicks > 10) {
         this.elapsedTicks = 10;
      }

      this.renderPartialTicks = this.elapsedPartialTicks;
   }
}
