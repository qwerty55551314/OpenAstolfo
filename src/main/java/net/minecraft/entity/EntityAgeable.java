package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAgeable extends EntityCreature {
   public int growingAge;
   public int field_175502_b;
   public int field_175503_c;
   private float ageWidth = -1.0F;
   private float ageHeight;
   private static Entity[] ac;

   public EntityAgeable(World worldIn) {
      super(worldIn);
   }

   public abstract EntityAgeable createChild(EntityAgeable var1);

   @Override
   public boolean interact(EntityPlayer player) {
      ItemStack itemstack = player.inventory.getCurrentItem();
      if (itemstack != null && itemstack.getItem() == Items.spawn_egg) {
         boolean flag = super.worldObj.isRemote;
         if (!flag) {
            Class oclass = EntityList.getClassFromID(itemstack.getMetadata());
            if (oclass != null && this.getClass() == oclass) {
               EntityAgeable entityageable = this.createChild(this);
               if (entityageable != null) {
                  entityageable.setGrowingAge(-24000);
                  entityageable.setLocationAndAngles(super.posX, super.posY, super.posZ, 0.0F, 0.0F);
                  super.worldObj.spawnEntityInWorld(entityageable);
                  if (itemstack.hasDisplayName()) {
                     entityageable.setCustomNameTag(itemstack.getDisplayName());
                  }

                  if (!player.capabilities.isCreativeMode) {
                     itemstack.stackSize--;
                     if (itemstack.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                     }
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(12, (byte)0);
   }

   public int getGrowingAge() {
      return super.worldObj.isRemote ? super.dataWatcher.getWatchableObjectByte(12) : this.growingAge;
   }

   public void func_175501_a(int i, boolean bl) {
      int i1 = this.getGrowingAge();
      i1 += i * 20;
      if (i1 > 0) {
         i1 = 0;
         if (i1 < 0) {
            this.onGrowingAdult();
         }
      }

      int j = i1 - i1;
      this.setGrowingAge(i1);
      if (bl) {
         this.field_175502_b += j;
         if (this.field_175503_c == 0) {
            this.field_175503_c = 40;
         }
      }

      if (this.getGrowingAge() == 0) {
         this.setGrowingAge(this.field_175502_b);
      }
   }

   public void addGrowth(int growth) {
      this.func_175501_a(growth, false);
   }

   public void setGrowingAge(int age) {
      super.dataWatcher.updateObject(12, (byte)MathHelper.clamp_int(age, -1, 1));
      this.growingAge = age;
      this.setScaleForAge(this.isChild());
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      super.writeEntityToNBT(tagCompound);
      tagCompound.setInteger("Age", this.getGrowingAge());
      tagCompound.setInteger("ForcedAge", this.field_175502_b);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      super.readEntityFromNBT(tagCompund);
      this.setGrowingAge(tagCompund.getInteger("Age"));
      this.field_175502_b = tagCompund.getInteger("ForcedAge");
   }

   @Override
   public void onLivingUpdate() {
      super.onLivingUpdate();
      boolean flag = super.worldObj.isRemote;
      if (flag) {
         if (this.field_175503_c > 0) {
            if (this.field_175503_c % 4 == 0) {
               super.worldObj
                  .spawnParticle(
                     EnumParticleTypes.VILLAGER_HAPPY,
                     super.posX + (double)(super.rand.nextFloat() * super.width * 2.0F) - (double)super.width,
                     super.posY + 0.5 + (double)(super.rand.nextFloat() * super.height),
                     super.posZ + (double)(super.rand.nextFloat() * super.width * 2.0F) - (double)super.width,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            this.field_175503_c--;
         }

         this.setScaleForAge(this.isChild());
      } else {
         int i = this.getGrowingAge();
         if (i < 0) {
            this.setGrowingAge(++i);
            if (i == 0) {
               this.onGrowingAdult();
            }
         } else if (i > 0) {
            this.setGrowingAge(--i);
         }
      }
   }

   public void onGrowingAdult() {
   }

   @Override
   public boolean isChild() {
      return this.getGrowingAge() < 0;
   }

   public void setScaleForAge(boolean bl) {
      this.setScale(bl ? 0.5F : 1.0F);
   }

   @Override
   public final void setSize(float width, float height) {
      float f;
      int i = (f = this.ageWidth - 0.0F) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
      boolean flag = i > 0;
      this.ageWidth = width;
      this.ageHeight = height;
      if (!flag) {
         this.setScale(1.0F);
      }
   }

   public final void setScale(float scale) {
      super.setSize(this.ageWidth * scale, this.ageHeight * scale);
   }

   public static void U(Entity[] entityArray) {
      ac = entityArray;
   }

   public static Entity[] h() {
      return ac;
   }

   static {
      U(new Entity[1]);
   }
}
