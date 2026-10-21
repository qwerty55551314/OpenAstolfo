package astolfo.util;

import java.util.Random;
import net.minecraft.util.Vector3d;

public class Rotator {
   private boolean randomize;
   private float smoothing;
   private Random random;
   private static final double RAD_TO_DEG = 180.0 / Math.PI;

   public Rotator(boolean bl, float f) {
      this.randomize = bl;
      this.smoothing = f;
      this.random = new Random();
   }

   public Rotation rotationTo(Vector3d vector3d, Vector3d vector3d2) {
      String s = "liu87";
      Rotation rotation = new Rotation();
      double d1 = vector3d.x;
      vector3d.x = d1 + ((double)(this.randomize ? this.randomBetween(-0.5F, 0.5F) : 0.0F) - vector3d2.x);
      double d2 = vector3d.y;
      vector3d.y = d2 + ((double)(this.randomize ? this.randomBetween(-0.25F, 0.5F) : 0.0F) - vector3d2.y);
      double d3 = vector3d.z;
      vector3d.z = d3 + ((double)(this.randomize ? this.randomBetween(-0.5F, 0.5F) : 0.0F) - vector3d2.z);
      double d0 = Math.hypot(vector3d.x, vector3d.z);
      rotation.setYaw((float)(Math.atan2(vector3d.z, vector3d.x) * (180.0 / Math.PI)) - 90.0F);
      rotation.setPitch(-((float)(Math.atan2(vector3d.y, d0) * (180.0 / Math.PI))));
      return rotation.wrap();
   }

   public Rotation smooth(Rotation rotation, Rotation rotation2) {
      Rotation rotation1 = new Rotation(rotation2.getYaw() - rotation.getYaw(), rotation2.getPitch() - rotation.getPitch()).wrap();
      rotation1.setYaw(rotation2.getYaw() - rotation1.getYaw() / 100.0F * this.smoothing);
      rotation1.setPitch(rotation2.getPitch() - rotation1.getPitch() / 100.0F * this.smoothing);
      return rotation1.wrap();
   }

   public Vector3d predict(Vector3d vector3d, Vector3d vector3d2, boolean bl, double d, double e) {
      e -= e % 2.0;
      e /= 2.0;
      double d2 = vector3d2.x;
      double d3 = e * d2;
      double d6;
      double d7;
      if (bl) {
         d6 = d3;
         d7 = d + 1.0;
      } else {
         d6 = d3;
         d7 = d;
      }

      double d0 = d6 * d7;
      double d4 = vector3d2.z;
      double d5 = e * d4;
      if (bl) {
         d6 = d5;
         d7 = d + 1.0;
      } else {
         d6 = d5;
         d7 = d;
      }

      double d1 = d6 * d7;
      return new Vector3d(vector3d.x + d0, vector3d.y, vector3d.z + d1);
   }

   public Rotation bezier(Rotation rotation, Rotation rotation2, float f) {
      float f1 = this.randomBetween(0.02F, 0.4F);
      float f1x = this.randomBetween(0.8F, 1.3F);
      Rotation rotation1 = this.lerp(rotation, rotation2, f1);
      Rotation rotation1x = this.lerp(rotation, rotation2, f1x);
      Rotation rotation21 = this.lerp(rotation, rotation1, f);
      Rotation rotation3 = this.lerp(rotation1, rotation1x, f);
      Rotation rotation4 = this.lerp(rotation1x, rotation2, f);
      Rotation rotation5 = this.lerp(rotation21, rotation3, f);
      Rotation rotation6 = this.lerp(rotation3, rotation4, f);
      Rotation rotation7 = this.lerp(rotation5, rotation6, f);
      return rotation7.wrap();
   }

   private Rotation lerp(Rotation rotation, Rotation rotation2, float f) {
      Rotation rotation1 = new Rotation();
      rotation1.setYaw(rotation.getYaw() + (rotation2.getYaw() - rotation.getYaw()) * f);
      rotation1.setPitch(rotation.getPitch() + (rotation2.getPitch() - rotation.getPitch()) * f);
      return rotation1;
   }

   public float randomBetween(float f, float g) {
      return f + this.random.nextFloat() * (g - f);
   }

   public void setRandomize(boolean bl) {
      this.randomize = bl;
   }

   public void setSmoothing(float f) {
      this.smoothing = f;
   }
}
