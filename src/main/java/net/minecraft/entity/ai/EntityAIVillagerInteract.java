package net.minecraft.entity.ai;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class EntityAIVillagerInteract extends EntityAIWatchClosest2 {
   private int interactionDelay;
   private EntityVillager villager;
   private static final long d = -8619613692188688374L;

   public EntityAIVillagerInteract(EntityVillager villagerIn) {
      super(villagerIn, EntityVillager.class, 3.0F, 0.02F);
      this.villager = villagerIn;
   }

   @Override
   public void startExecuting() {
      super.startExecuting();
      if (this.villager.canAbondonItems() && super.closestEntity instanceof EntityVillager && ((EntityVillager)super.closestEntity).func_175557_cr()) {
         this.interactionDelay = (int)d;
      } else {
         this.interactionDelay = 0;
      }
   }

   @Override
   public void updateTask() {
      super.updateTask();
      int l = this.interactionDelay;
      if (l > 0) {
         this.interactionDelay--;
         if (this.interactionDelay == 0) {
            InventoryBasic inventorybasic = this.villager.getVillagerInventory();

            for (int i = 0; i < inventorybasic.getSizeInventory(); i++) {
               ItemStack itemstack = inventorybasic.getStackInSlot(i);
               ItemStack itemstack1 = null;
               if (itemstack != null) {
                  label38: {
                     Item item;
                     label37: {
                        item = itemstack.getItem();
                        Item item1 = Items.bread;
                        if (item != item1) {
                           Item item2 = Items.potato;
                           if (item != item2) {
                              Item item3 = Items.carrot;
                              if (item != item3) {
                                 break label37;
                              }
                           }
                        }

                        if (itemstack.stackSize > 3) {
                           int j = itemstack.stackSize / 2;
                           itemstack.stackSize -= j;
                           itemstack1 = new ItemStack(item, j, itemstack.getMetadata());
                           break label38;
                        }
                     }

                     if (item == Items.wheat) {
                        int i1 = itemstack.stackSize;
                        if (i1 > 5) {
                           int j1 = itemstack.stackSize / 2 / 3 * 3;
                           int k = j1 / 3;
                           itemstack.stackSize -= j1;
                           itemstack1 = new ItemStack(Items.bread, k, 0);
                        }
                     }
                  }

                  if (itemstack.stackSize <= 0) {
                     inventorybasic.setInventorySlotContents(i, (ItemStack)null);
                  }
               }

               if (itemstack1 != null) {
                  double d0 = this.villager.posY - 0.3F + (double)this.villager.getEyeHeight();
                  EntityItem entityitem = new EntityItem(this.villager.worldObj, this.villager.posX, d0, this.villager.posZ, itemstack1);
                  float f = 0.3F;
                  float f1 = this.villager.rotationYawHead;
                  float f2 = this.villager.rotationPitch;
                  entityitem.motionX = (double)(-MathHelper.sin(f1 / 180.0F * (float) Math.PI) * MathHelper.cos(f2 / 180.0F * (float) Math.PI) * f);
                  entityitem.motionZ = (double)(MathHelper.cos(f1 / 180.0F * (float) Math.PI) * MathHelper.cos(f2 / 180.0F * (float) Math.PI) * f);
                  entityitem.motionY = (double)(-MathHelper.sin(f2 / 180.0F * (float) Math.PI) * f + 0.1F);
                  entityitem.setDefaultPickupDelay();
                  this.villager.worldObj.spawnEntityInWorld(entityitem);
                  break;
               }
            }
         }
      }
   }
}
