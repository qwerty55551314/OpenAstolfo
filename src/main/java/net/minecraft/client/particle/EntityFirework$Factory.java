package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class EntityFirework$Factory implements IParticleFactory {
   @Override
   public EntityFX getEntityFX(
      int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... ints
   ) {
      EntityFirework$SparkFX entityfirework$sparkfx = new EntityFirework$SparkFX(
         worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Minecraft.getMinecraft().effectRenderer
      );
      entityfirework$sparkfx.setAlphaF(0.99F);
      return entityfirework$sparkfx;
   }
}
