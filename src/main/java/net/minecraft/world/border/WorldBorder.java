package net.minecraft.world.border;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

public class WorldBorder {
   private final List<IBorderListener> listeners = Lists.newArrayList();
   private double centerX = 0.0;
   private double centerZ = 0.0;
   private double startDiameter = 6.0E7;
   private double endDiameter = this.startDiameter;
   private long endTime;
   private long startTime;
   private int worldSize = 29999984;
   private double damageAmount = 0.2;
   private double damageBuffer = 5.0;
   private int warningTime = 15;
   private int warningDistance = 5;
   private static Entity[] B;

   public boolean contains(BlockPos pos) {
      double d0;
      int i = (d0 = (double)(pos.getX() + 1) - this.minX()) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
      if (i > 0) {
         double d1;
         int j = (d1 = (double)pos.getX() - this.maxX()) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
         if (j < 0) {
            double d2;
            int k = (d2 = (double)(pos.getZ() + 1) - this.minZ()) == 0.0 ? 0 : (d2 < 0.0 ? -1 : 1);
            if (k > 0) {
               double d3;
               int l = (d3 = (double)pos.getZ() - this.maxZ()) == 0.0 ? 0 : (d3 < 0.0 ? -1 : 1);
               if (l < 0) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean contains(ChunkCoordIntPair range) {
      double d0;
      int i = (d0 = (double)range.getXEnd() - this.minX()) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
      if (i > 0) {
         double d1;
         int j = (d1 = (double)range.getXStart() - this.maxX()) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
         if (j < 0) {
            double d2;
            int k = (d2 = (double)range.getZEnd() - this.minZ()) == 0.0 ? 0 : (d2 < 0.0 ? -1 : 1);
            if (k > 0) {
               double d3;
               int l = (d3 = (double)range.getZStart() - this.maxZ()) == 0.0 ? 0 : (d3 < 0.0 ? -1 : 1);
               if (l < 0) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean contains(AxisAlignedBB bb) {
      double d0;
      int i = (d0 = bb.maxX - this.minX()) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
      if (i > 0) {
         double d1;
         int j = (d1 = bb.minX - this.maxX()) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
         if (j < 0) {
            double d2;
            int k = (d2 = bb.maxZ - this.minZ()) == 0.0 ? 0 : (d2 < 0.0 ? -1 : 1);
            if (k > 0) {
               double d3;
               int l = (d3 = bb.minZ - this.maxZ()) == 0.0 ? 0 : (d3 < 0.0 ? -1 : 1);
               if (l < 0) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public double getClosestDistance(Entity entityIn) {
      return this.getClosestDistance(entityIn.posX, entityIn.posZ);
   }

   public double getClosestDistance(double x, double z) {
      double d0 = z - this.minZ();
      double d1 = this.maxZ() - z;
      double d2 = x - this.minX();
      double d3 = this.maxX() - x;
      double d4 = Math.min(d2, d3);
      d4 = Math.min(d4, d0);
      return Math.min(d4, d1);
   }

   public EnumBorderStatus getStatus() {
      return this.endDiameter < this.startDiameter
         ? EnumBorderStatus.SHRINKING
         : (this.endDiameter > this.startDiameter ? EnumBorderStatus.GROWING : EnumBorderStatus.STATIONARY);
   }

   public double minX() {
      double d0 = this.getCenterX() - this.getDiameter() / 2.0;
      if (d0 < (double)(-this.worldSize)) {
         d0 = (double)(-this.worldSize);
      }

      return d0;
   }

   public double minZ() {
      double d0 = this.getCenterZ() - this.getDiameter() / 2.0;
      if (d0 < (double)(-this.worldSize)) {
         d0 = (double)(-this.worldSize);
      }

      return d0;
   }

   public double maxX() {
      double d0 = this.getCenterX() + this.getDiameter() / 2.0;
      if (d0 > (double)this.worldSize) {
         d0 = (double)this.worldSize;
      }

      return d0;
   }

   public double maxZ() {
      double d0 = this.getCenterZ() + this.getDiameter() / 2.0;
      if (d0 > (double)this.worldSize) {
         d0 = (double)this.worldSize;
      }

      return d0;
   }

   public double getCenterX() {
      return this.centerX;
   }

   public double getCenterZ() {
      return this.centerZ;
   }

   public void setCenter(double x, double z) {
      this.centerX = x;
      this.centerZ = z;

      for (IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onCenterChanged(this, x, z);
      }
   }

   public double getDiameter() {
      if (this.getStatus() != EnumBorderStatus.STATIONARY) {
         double d0 = (double)((float)(System.currentTimeMillis() - this.startTime) / (float)(this.endTime - this.startTime));
         if (d0 < 1.0) {
            return this.startDiameter + (this.endDiameter - this.startDiameter) * d0;
         }

         this.setTransition(this.endDiameter);
      }

      return this.startDiameter;
   }

   public long getTimeUntilTarget() {
      return this.getStatus() != EnumBorderStatus.STATIONARY ? this.endTime - System.currentTimeMillis() : 0L;
   }

   public double getTargetSize() {
      return this.endDiameter;
   }

   public void setTransition(double newSize) {
      this.startDiameter = newSize;
      this.endDiameter = newSize;
      this.endTime = System.currentTimeMillis();
      this.startTime = this.endTime;

      for (IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onSizeChanged(this, newSize);
      }
   }

   public void setTransition(double oldSize, double newSize, long time) {
      this.startDiameter = oldSize;
      this.endDiameter = newSize;
      this.startTime = System.currentTimeMillis();
      this.endTime = this.startTime + time;

      for (IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onTransitionStarted(this, oldSize, newSize, time);
      }
   }

   public List<IBorderListener> getListeners() {
      return Lists.newArrayList(this.listeners);
   }

   public void addListener(IBorderListener listener) {
      this.listeners.add(listener);
   }

   public void setSize(int size) {
      this.worldSize = size;
   }

   public int getSize() {
      return this.worldSize;
   }

   public double getDamageBuffer() {
      return this.damageBuffer;
   }

   public void setDamageBuffer(double bufferSize) {
      this.damageBuffer = bufferSize;

      for (IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onDamageBufferChanged(this, bufferSize);
      }
   }

   public double getDamageAmount() {
      return this.damageAmount;
   }

   public void setDamageAmount(double newAmount) {
      this.damageAmount = newAmount;

      for (IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onDamageAmountChanged(this, newAmount);
      }
   }

   public double getResizeSpeed() {
      return this.endTime == this.startTime ? 0.0 : Math.abs(this.startDiameter - this.endDiameter) / (double)(this.endTime - this.startTime);
   }

   public int getWarningTime() {
      return this.warningTime;
   }

   public void setWarningTime(int warningTime) {
      this.warningTime = warningTime;

      for (IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onWarningTimeChanged(this, warningTime);
      }
   }

   public int getWarningDistance() {
      return this.warningDistance;
   }

   public void setWarningDistance(int warningDistance) {
      this.warningDistance = warningDistance;

      for (IBorderListener iborderlistener : this.getListeners()) {
         iborderlistener.onWarningDistanceChanged(this, warningDistance);
      }
   }

   public static void b(Entity[] entityArray) {
      B = entityArray;
   }

   public static Entity[] M() {
      return B;
   }

   static {
      b(new Entity[1]);
   }
}
