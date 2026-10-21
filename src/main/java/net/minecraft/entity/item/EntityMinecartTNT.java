package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMinecartTNT extends EntityMinecart {
   private int minecartTNTFuse = -1;

   public EntityMinecartTNT(World worldIn) {
      super(worldIn);
   }

   public EntityMinecartTNT(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.TNT;
   }

   @Override
   public IBlockState getDefaultDisplayTile() {
      return Blocks.tnt.getDefaultState();
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      int i = this.minecartTNTFuse;
      if (i > 0) {
         this.minecartTNTFuse--;
         super.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, super.posX, super.posY + 0.5, super.posZ, 0.0, 0.0, 0.0);
      } else {
         int j = this.minecartTNTFuse;
         if (j == 0) {
            this.explodeCart(super.motionX * super.motionX + super.motionZ * super.motionZ);
         }
      }

      if (super.isCollidedHorizontally) {
         double d0 = super.motionX * super.motionX + super.motionZ * super.motionZ;
         if (d0 >= 0.01F) {
            this.explodeCart(d0);
         }
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      Entity entity = source.getSourceOfDamage();
      if (entity instanceof EntityArrow) {
         EntityArrow entityarrow = (EntityArrow)entity;
         if (entityarrow.isBurning()) {
            this.explodeCart(entityarrow.motionX * entityarrow.motionX + entityarrow.motionY * entityarrow.motionY + entityarrow.motionZ * entityarrow.motionZ);
         }
      }

      return super.attackEntityFrom(source, amount);
   }

   @Override
   public void killMinecart(DamageSource source) {
      super.killMinecart(source);
      double d0 = super.motionX * super.motionX + super.motionZ * super.motionZ;
      boolean flag = source.isExplosion();
      if (!flag && super.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         this.entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0F);
      }

      boolean flag1 = source.isFireDamage();
      if (!flag1) {
         boolean flag2 = source.isExplosion();
         if (!flag2 && !(d0 >= 0.01F)) {
            return;
         }
      }

      this.explodeCart(d0);
   }

   public void explodeCart(double d) {
      if (!super.worldObj.isRemote) {
         double d0 = Math.sqrt(d);
         if (d0 > 5.0) {
            d0 = 5.0;
         }

         super.worldObj.createExplosion(this, super.posX, super.posY, super.posZ, (float)(4.0 + super.rand.nextDouble() * 1.5 * d0), true);
         this.setDead();
      }
   }

   @Override
   public void fall(float distance, float damageMultiplier) {
      if (distance >= 3.0F) {
         float f = distance / 10.0F;
         this.explodeCart((double)(f * f));
      }

      super.fall(distance, damageMultiplier);
   }

   @Override
   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
      if (receivingPower && this.minecartTNTFuse < 0) {
         this.ignite();
      }
   }

   @Override
   public void handleStatusUpdate(byte id) {
      if (id == 10) {
         this.ignite();
      } else {
         super.handleStatusUpdate(id);
      }
   }

   public void ignite() {
      this.minecartTNTFuse = 80;
      if (!super.worldObj.isRemote) {
         super.worldObj.setEntityState(this, (byte)10);
         if (!this.isSilent()) {
            super.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0F, 1.0F);
         }
      }
   }

   public int getFuseTicks() {
      return this.minecartTNTFuse;
   }

   public boolean isIgnited() {
      return this.minecartTNTFuse > -1;
   }

   @Override
   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
      return this.isIgnited() && (BlockRailBase.isRailBlock(blockStateIn) || BlockRailBase.isRailBlock(worldIn, pos.up()))
         ? 0.0F
         : super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
   }

   @Override
   public boolean verifyExplosion(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float f) {
      return this.isIgnited() && (BlockRailBase.isRailBlock(blockStateIn) || BlockRailBase.isRailBlock(worldIn, pos.up()))
         ? false
         : super.verifyExplosion(explosionIn, worldIn, pos, blockStateIn, f);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      super.readEntityFromNBT(tagCompund);
      if (tagCompund.hasKey("TNTFuse", 99)) {
         this.minecartTNTFuse = tagCompund.getInteger("TNTFuse");
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      super.writeEntityToNBT(tagCompound);
      tagCompound.setInteger("TNTFuse", this.minecartTNTFuse);
   }
}
