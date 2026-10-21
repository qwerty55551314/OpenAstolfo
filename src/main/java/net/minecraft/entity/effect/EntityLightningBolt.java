package net.minecraft.entity.effect;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityLightningBolt extends EntityWeatherEffect {
   private int lightningState;
   public long boltVertex;
   private int boltLivingTime;

   public EntityLightningBolt(World worldIn, double posX, double posY, double posZ) {
      super(worldIn);
      this.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
      this.lightningState = 2;
      this.boltVertex = super.rand.nextLong();
      this.boltLivingTime = super.rand.nextInt(3) + 1;
      BlockPos blockpos = new BlockPos(this);
      if (!worldIn.isRemote
         && worldIn.getGameRules().getBoolean("doFireTick")
         && (worldIn.getDifficulty() == EnumDifficulty.NORMAL || worldIn.getDifficulty() == EnumDifficulty.HARD)
         && worldIn.isAreaLoaded(blockpos, 10)) {
         if (worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(worldIn, blockpos)) {
            worldIn.setBlockState(blockpos, Blocks.fire.getDefaultState());
         }

         for (int i = 0; i < 4; i++) {
            BlockPos blockpos1 = blockpos.add(super.rand.nextInt(3) - 1, super.rand.nextInt(3) - 1, super.rand.nextInt(3) - 1);
            if (worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(worldIn, blockpos1)) {
               worldIn.setBlockState(blockpos1, Blocks.fire.getDefaultState());
            }
         }
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      int j = this.lightningState;
      if (j == 2) {
         super.worldObj.playSoundEffect(super.posX, super.posY, super.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + super.rand.nextFloat() * 0.2F);
         super.worldObj.playSoundEffect(super.posX, super.posY, super.posZ, "random.explode", 2.0F, 0.5F + super.rand.nextFloat() * 0.2F);
      }

      this.lightningState--;
      int k = this.lightningState;
      if (k < 0) {
         if (this.boltLivingTime == 0) {
            this.setDead();
         } else if (this.lightningState < -super.rand.nextInt(10)) {
            this.boltLivingTime--;
            this.lightningState = 1;
            this.boltVertex = super.rand.nextLong();
            BlockPos blockpos = new BlockPos(this);
            if (!super.worldObj.isRemote
               && super.worldObj.getGameRules().getBoolean("doFireTick")
               && super.worldObj.isAreaLoaded(blockpos, 10)
               && super.worldObj.getBlockState(blockpos).getBlock().getMaterial() == Material.air
               && Blocks.fire.canPlaceBlockAt(super.worldObj, blockpos)) {
               super.worldObj.setBlockState(blockpos, Blocks.fire.getDefaultState());
            }
         }
      }

      int l = this.lightningState;
      if (l >= 0) {
         if (super.worldObj.isRemote) {
            super.worldObj.setLastLightningBolt(2);
         } else {
            double d0 = 3.0;
            List list = super.worldObj
               .getEntitiesWithinAABBExcludingEntity(
                  this, new AxisAlignedBB(super.posX - d0, super.posY - d0, super.posZ - d0, super.posX + d0, super.posY + 6.0 + d0, super.posZ + d0)
               );

            for (int i = 0; i < list.size(); i++) {
               Entity entity = (Entity)list.get(i);
               entity.onStruckByLightning(this);
            }
         }
      }
   }

   @Override
   public void entityInit() {
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
   }
}
