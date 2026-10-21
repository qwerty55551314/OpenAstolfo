package net.minecraft.village;

import java.util.Iterator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;

public class VillageSiege {
   private World worldObj;
   private boolean field_75535_b;
   private int field_75536_c = -1;
   private int field_75533_d;
   private int field_75534_e;
   private Village theVillage;
   private int field_75532_g;
   private int field_75538_h;
   private int field_75539_i;

   public VillageSiege(World worldIn) {
      this.worldObj = worldIn;
   }

   public void tick() {
      boolean flag = this.worldObj.isDaytime();
      if (flag) {
         this.field_75536_c = 0;
      } else {
         int i = this.field_75536_c;
         if (i != 2) {
            int j = this.field_75536_c;
            if (j == 0) {
               float f = this.worldObj.getCelestialAngle(0.0F);
               if ((double)f < 0.5 || (double)f > 0.501) {
                  return;
               }

               this.field_75536_c = this.worldObj.rand.nextInt(10) == 0 ? 1 : 2;
               this.field_75535_b = false;
               if (this.field_75536_c == 2) {
                  return;
               }
            }

            int k = this.field_75536_c;
            if (k != -1) {
               boolean flag1 = this.field_75535_b;
               if (!flag1) {
                  if (!this.func_75529_b()) {
                     return;
                  }

                  this.field_75535_b = true;
               }

               int l = this.field_75534_e;
               if (l > 0) {
                  this.field_75534_e--;
               } else {
                  this.field_75534_e = 2;
                  if (this.field_75533_d > 0) {
                     this.spawnZombie();
                     this.field_75533_d--;
                  } else {
                     this.field_75536_c = 2;
                  }
               }
            }
         }
      }
   }

   private boolean func_75529_b() {
      for (EntityPlayer entityplayer : this.worldObj.playerEntities) {
         if (!entityplayer.isSpectator()) {
            this.theVillage = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(entityplayer), 1);
            Village village1 = this.theVillage;
            if (village1 != null) {
               int j = this.theVillage.getNumVillageDoors();
               if (j >= 10) {
                  int k = this.theVillage.getTicksSinceLastDoorAdding();
                  if (k >= 20 && this.theVillage.getNumVillagers() >= 20) {
                     BlockPos blockpos = this.theVillage.getCenter();
                     float f = (float)this.theVillage.getVillageRadius();
                     boolean flag = false;

                     for (int i = 0; i < 10; i++) {
                        float f1 = this.worldObj.rand.nextFloat() * (float) Math.PI * 2.0F;
                        this.field_75532_g = blockpos.getX() + (int)((double)(MathHelper.cos(f1) * f) * 0.9);
                        this.field_75538_h = blockpos.getY();
                        this.field_75539_i = blockpos.getZ() + (int)((double)(MathHelper.sin(f1) * f) * 0.9);
                        flag = false;
                        Iterator iterator1 = this.worldObj.getVillageCollection().getVillageList().iterator();
                        Iterator iterator = iterator1;

                        while (iterator.hasNext()) {
                           Village village = (Village)iterator.next();
                           if (village != this.theVillage && village.func_179866_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) {
                              flag = true;
                              break;
                           }
                        }

                        if (!flag) {
                           break;
                        }
                     }

                     if (flag) {
                        return false;
                     }

                     Vec3 vec3 = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
                     if (vec3 != null) {
                        this.field_75534_e = 0;
                        this.field_75533_d = 20;
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   private boolean spawnZombie() {
      Vec3 vec3 = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
      if (vec3 == null) {
         return false;
      } else {
         EntityZombie entityzombie;
         try {
            entityzombie = new EntityZombie(this.worldObj);
            entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityzombie)), (IEntityLivingData)null);
            entityzombie.setVillager(false);
         } catch (Exception exception) {
            exception.printStackTrace();
            return false;
         }

         entityzombie.setLocationAndAngles(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
         this.worldObj.spawnEntityInWorld(entityzombie);
         BlockPos blockpos = this.theVillage.getCenter();
         entityzombie.setHomePosAndDistance(blockpos, this.theVillage.getVillageRadius());
         return true;
      }
   }

   private Vec3 func_179867_a(BlockPos blockPos) {
      for (int i = 0; i < 10; i++) {
         BlockPos blockpos = blockPos.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
         if (this.theVillage.func_179866_a(blockpos)
            && SpawnerAnimals.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, this.worldObj, blockpos)) {
            return new Vec3((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
         }
      }

      return null;
   }
}
