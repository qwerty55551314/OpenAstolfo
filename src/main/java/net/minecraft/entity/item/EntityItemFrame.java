package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class EntityItemFrame extends EntityHanging {
   private float itemDropChance = 1.0F;

   public EntityItemFrame(World worldIn) {
      super(worldIn);
   }

   public EntityItemFrame(World worldIn, BlockPos blockPos, EnumFacing enumFacing) {
      super(worldIn, blockPos);
      this.updateFacingWithBoundingBox(enumFacing);
   }

   @Override
   public void entityInit() {
      this.getDataWatcher().addObjectByDataType(8, 5);
      this.getDataWatcher().addObject(9, (byte)0);
   }

   @Override
   public float getCollisionBorderSize() {
      return 0.0F;
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      boolean flag = this.isEntityInvulnerable(source);
      if (flag) {
         return false;
      } else {
         boolean flag1 = source.isExplosion();
         if (!flag1 && this.getDisplayedItem() != null) {
            if (!super.worldObj.isRemote) {
               this.dropItemOrSelf(source.getEntity(), false);
               this.setDisplayedItem((ItemStack)null);
            }

            return true;
         } else {
            return super.attackEntityFrom(source, amount);
         }
      }
   }

   @Override
   public int getWidthPixels() {
      return 12;
   }

   @Override
   public int getHeightPixels() {
      return 12;
   }

   @Override
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = 16.0;
      d0 = d0 * 64.0 * super.renderDistanceWeight;
      return distance < d0 * d0;
   }

   @Override
   public void onBroken(Entity brokenEntity) {
      this.dropItemOrSelf(brokenEntity, true);
   }

   public void dropItemOrSelf(Entity entity, boolean bl) {
      if (super.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         ItemStack itemstack = this.getDisplayedItem();
         boolean flag = entity instanceof EntityPlayer;
         if (flag) {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (entityplayer.capabilities.isCreativeMode) {
               this.removeFrameFromMap(itemstack);
               return;
            }
         }

         if (bl) {
            this.entityDropItem(new ItemStack(Items.item_frame), 0.0F);
         }

         if (itemstack != null && super.rand.nextFloat() < this.itemDropChance) {
            itemstack = itemstack.copy();
            this.removeFrameFromMap(itemstack);
            this.entityDropItem(itemstack, 0.0F);
         }
      }
   }

   private void removeFrameFromMap(ItemStack itemStack) {
      if (itemStack != null) {
         if (itemStack.getItem() == Items.filled_map) {
            MapData mapdata = ((ItemMap)itemStack.getItem()).getMapData(itemStack, super.worldObj);
            mapdata.mapDecorations.remove("frame-" + this.getEntityId());
         }

         itemStack.setItemFrame((EntityItemFrame)null);
      }
   }

   public ItemStack getDisplayedItem() {
      return this.getDataWatcher().getWatchableObjectItemStack(8);
   }

   public void setDisplayedItem(ItemStack itemStack) {
      this.setDisplayedItemWithUpdate(itemStack, true);
   }

   private void setDisplayedItemWithUpdate(ItemStack itemStack, boolean bl) {
      if (itemStack != null) {
         itemStack = itemStack.copy();
         itemStack.stackSize = 1;
         itemStack.setItemFrame(this);
      }

      this.getDataWatcher().updateObject(8, itemStack);
      this.getDataWatcher().setObjectWatched(8);
      if (bl && super.hangingPosition != null) {
         super.worldObj.updateComparatorOutputLevel(super.hangingPosition, Blocks.air);
      }
   }

   public int getRotation() {
      return this.getDataWatcher().getWatchableObjectByte(9);
   }

   public void setItemRotation(int i) {
      this.func_174865_a(i, true);
   }

   private void func_174865_a(int i, boolean bl) {
      this.getDataWatcher().updateObject(9, (byte)(i % 8));
      if (bl && super.hangingPosition != null) {
         super.worldObj.updateComparatorOutputLevel(super.hangingPosition, Blocks.air);
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      if (this.getDisplayedItem() != null) {
         tagCompound.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
         tagCompound.setByte("ItemRotation", (byte)this.getRotation());
         tagCompound.setFloat("ItemDropChance", this.itemDropChance);
      }

      super.writeEntityToNBT(tagCompound);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Item");
      if (nbttagcompound != null && !nbttagcompound.hasNoTags()) {
         this.setDisplayedItemWithUpdate(ItemStack.loadItemStackFromNBT(nbttagcompound), false);
         this.func_174865_a(tagCompund.getByte("ItemRotation"), false);
         if (tagCompund.hasKey("ItemDropChance", 99)) {
            this.itemDropChance = tagCompund.getFloat("ItemDropChance");
         }

         if (tagCompund.hasKey("Direction")) {
            this.func_174865_a(this.getRotation() * 2, false);
         }
      }

      super.readEntityFromNBT(tagCompund);
   }

   @Override
   public boolean interactFirst(EntityPlayer playerIn) {
      if (this.getDisplayedItem() == null) {
         ItemStack itemstack = playerIn.getHeldItem();
         if (itemstack != null && !super.worldObj.isRemote) {
            this.setDisplayedItem(itemstack);
            if (!playerIn.capabilities.isCreativeMode && --itemstack.stackSize <= 0) {
               playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack)null);
            }
         }
      } else {
         boolean flag = super.worldObj.isRemote;
         if (!flag) {
            this.setItemRotation(this.getRotation() + 1);
         }
      }

      return true;
   }

   public int func_174866_q() {
      return this.getDisplayedItem() == null ? 0 : this.getRotation() % 8 + 1;
   }
}
