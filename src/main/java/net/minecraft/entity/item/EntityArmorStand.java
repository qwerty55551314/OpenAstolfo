package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotations;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityArmorStand extends EntityLivingBase {
   private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
   private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
   private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
   private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
   private final ItemStack[] contents = new ItemStack[5];
   private boolean canInteract;
   private long punchCooldown;
   private int disabledSlots;
   private boolean field_181028_bj;
   private Rotations headRotation = DEFAULT_HEAD_ROTATION;
   private Rotations bodyRotation = DEFAULT_BODY_ROTATION;
   private Rotations leftArmRotation = DEFAULT_LEFTARM_ROTATION;
   private Rotations rightArmRotation = DEFAULT_RIGHTARM_ROTATION;
   private Rotations leftLegRotation = DEFAULT_LEFTLEG_ROTATION;
   private Rotations rightLegRotation = DEFAULT_RIGHTLEG_ROTATION;

   public EntityArmorStand(World worldIn) {
      super(worldIn);
      this.setSilent(true);
      super.noClip = this.hasNoGravity();
      this.setSize(0.5F, 1.975F);
   }

   public EntityArmorStand(World worldIn, double posX, double posY, double posZ) {
      this(worldIn);
      this.setPosition(posX, posY, posZ);
   }

   @Override
   public boolean isServerWorld() {
      boolean flag = super.isServerWorld();
      if (flag) {
         boolean flag1 = this.hasNoGravity();
         if (!flag1) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(10, (byte)0);
      super.dataWatcher.addObject(11, DEFAULT_HEAD_ROTATION);
      super.dataWatcher.addObject(12, DEFAULT_BODY_ROTATION);
      super.dataWatcher.addObject(13, DEFAULT_LEFTARM_ROTATION);
      super.dataWatcher.addObject(14, DEFAULT_RIGHTARM_ROTATION);
      super.dataWatcher.addObject(15, DEFAULT_LEFTLEG_ROTATION);
      super.dataWatcher.addObject(16, DEFAULT_RIGHTLEG_ROTATION);
   }

   @Override
   public ItemStack getHeldItem() {
      return this.contents[0];
   }

   @Override
   public ItemStack getEquipmentInSlot(int slotIn) {
      return this.contents[slotIn];
   }

   @Override
   public ItemStack getCurrentArmor(int slotIn) {
      return this.contents[slotIn + 1];
   }

   @Override
   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
      this.contents[slotIn] = stack;
   }

   @Override
   public ItemStack[] getInventory() {
      return this.contents;
   }

   @Override
   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
      int i;
      if (inventorySlot == 99) {
         i = 0;
      } else {
         i = inventorySlot - 100 + 1;
         if (i < 0 || i >= this.contents.length) {
            return false;
         }
      }

      if (itemStackIn == null || EntityLiving.getArmorPosition(itemStackIn) == i || i == 4 && itemStackIn.getItem() instanceof ItemBlock) {
         this.setCurrentItemOrArmor(i, itemStackIn);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound tagCompound) {
      super.writeEntityToNBT(tagCompound);
      NBTTagList nbttaglist = new NBTTagList();

      for (int i = 0; i < this.contents.length; i++) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         if (this.contents[i] != null) {
            this.contents[i].writeToNBT(nbttagcompound);
         }

         nbttaglist.appendTag(nbttagcompound);
      }

      tagCompound.setTag("Equipment", nbttaglist);
      if (this.getAlwaysRenderNameTag() && (this.getCustomNameTag() == null || this.getCustomNameTag().length() == 0)) {
         tagCompound.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
      }

      tagCompound.setBoolean("Invisible", this.isInvisible());
      tagCompound.setBoolean("Small", this.isSmall());
      tagCompound.setBoolean("ShowArms", this.getShowArms());
      tagCompound.setInteger("DisabledSlots", this.disabledSlots);
      tagCompound.setBoolean("NoGravity", this.hasNoGravity());
      tagCompound.setBoolean("NoBasePlate", this.hasNoBasePlate());
      if (this.hasMarker()) {
         tagCompound.setBoolean("Marker", this.hasMarker());
      }

      tagCompound.setTag("Pose", this.readPoseFromNBT());
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound tagCompund) {
      super.readEntityFromNBT(tagCompund);
      if (tagCompund.hasKey("Equipment", 9)) {
         NBTTagList nbttaglist = tagCompund.getTagList("Equipment", 10);

         for (int i = 0; i < this.contents.length; i++) {
            this.contents[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
         }
      }

      this.setInvisible(tagCompund.getBoolean("Invisible"));
      this.setSmall(tagCompund.getBoolean("Small"));
      this.setShowArms(tagCompund.getBoolean("ShowArms"));
      this.disabledSlots = tagCompund.getInteger("DisabledSlots");
      this.setNoGravity(tagCompund.getBoolean("NoGravity"));
      this.setNoBasePlate(tagCompund.getBoolean("NoBasePlate"));
      this.setMarker(tagCompund.getBoolean("Marker"));
      this.field_181028_bj = !this.hasMarker();
      super.noClip = this.hasNoGravity();
      NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Pose");
      this.writePoseToNBT(nbttagcompound);
   }

   private void writePoseToNBT(NBTTagCompound tagCompound) {
      NBTTagList nbttaglist = tagCompound.getTagList("Head", 5);
      if (nbttaglist.tagCount() > 0) {
         this.setHeadRotation(new Rotations(nbttaglist));
      } else {
         this.setHeadRotation(DEFAULT_HEAD_ROTATION);
      }

      NBTTagList nbttaglist1 = tagCompound.getTagList("Body", 5);
      if (nbttaglist1.tagCount() > 0) {
         this.setBodyRotation(new Rotations(nbttaglist1));
      } else {
         this.setBodyRotation(DEFAULT_BODY_ROTATION);
      }

      NBTTagList nbttaglist2 = tagCompound.getTagList("LeftArm", 5);
      if (nbttaglist2.tagCount() > 0) {
         this.setLeftArmRotation(new Rotations(nbttaglist2));
      } else {
         this.setLeftArmRotation(DEFAULT_LEFTARM_ROTATION);
      }

      NBTTagList nbttaglist3 = tagCompound.getTagList("RightArm", 5);
      if (nbttaglist3.tagCount() > 0) {
         this.setRightArmRotation(new Rotations(nbttaglist3));
      } else {
         this.setRightArmRotation(DEFAULT_RIGHTARM_ROTATION);
      }

      NBTTagList nbttaglist4 = tagCompound.getTagList("LeftLeg", 5);
      if (nbttaglist4.tagCount() > 0) {
         this.setLeftLegRotation(new Rotations(nbttaglist4));
      } else {
         this.setLeftLegRotation(DEFAULT_LEFTLEG_ROTATION);
      }

      NBTTagList nbttaglist5 = tagCompound.getTagList("RightLeg", 5);
      if (nbttaglist5.tagCount() > 0) {
         this.setRightLegRotation(new Rotations(nbttaglist5));
      } else {
         this.setRightLegRotation(DEFAULT_RIGHTLEG_ROTATION);
      }
   }

   private NBTTagCompound readPoseFromNBT() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      boolean flag = DEFAULT_HEAD_ROTATION.equals(this.headRotation);
      if (!flag) {
         nbttagcompound.setTag("Head", this.headRotation.writeToNBT());
      }

      boolean flag1 = DEFAULT_BODY_ROTATION.equals(this.bodyRotation);
      if (!flag1) {
         nbttagcompound.setTag("Body", this.bodyRotation.writeToNBT());
      }

      boolean flag2 = DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation);
      if (!flag2) {
         nbttagcompound.setTag("LeftArm", this.leftArmRotation.writeToNBT());
      }

      boolean flag3 = DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation);
      if (!flag3) {
         nbttagcompound.setTag("RightArm", this.rightArmRotation.writeToNBT());
      }

      boolean flag4 = DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation);
      if (!flag4) {
         nbttagcompound.setTag("LeftLeg", this.leftLegRotation.writeToNBT());
      }

      if (!DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation)) {
         nbttagcompound.setTag("RightLeg", this.rightLegRotation.writeToNBT());
      }

      return nbttagcompound;
   }

   @Override
   public boolean canBePushed() {
      return false;
   }

   @Override
   public void collideWithEntity(Entity entityIn) {
   }

   @Override
   public void collideWithNearbyEntities() {
      List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
      if (list != null) {
         boolean flag = list.isEmpty();
         if (!flag) {
            for (int i = 0; i < list.size(); i++) {
               Entity entity = (Entity)list.get(i);
               if (entity instanceof EntityMinecart
                  && ((EntityMinecart)entity).getMinecartType() == EntityMinecart.EnumMinecartType.RIDEABLE
                  && this.getDistanceSqToEntity(entity) <= 0.2) {
                  entity.applyEntityCollision(this);
               }
            }
         }
      }
   }

   @Override
   public boolean interactAt(EntityPlayer player, Vec3 targetVec3) {
      boolean flag3 = this.hasMarker();
      if (flag3) {
         return false;
      } else {
         boolean flag4 = super.worldObj.isRemote;
         if (!flag4 && !player.isSpectator()) {
            int b0 = 0;
            ItemStack itemstack = player.getCurrentEquippedItem();
            boolean flag = itemstack != null;
            if (flag && itemstack.getItem() instanceof ItemArmor) {
               ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
               if (itemarmor.armorType == 3) {
                  b0 = 1;
               } else if (itemarmor.armorType == 2) {
                  b0 = 2;
               } else if (itemarmor.armorType == 1) {
                  b0 = 3;
               } else if (itemarmor.armorType == 0) {
                  b0 = 4;
               }
            }

            if (flag && (itemstack.getItem() == Items.skull || itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin))) {
               b0 = 4;
            }

            int b1;
            label141: {
               b1 = 0;
               boolean flag1 = this.isSmall();
               double d0 = flag1 ? targetVec3.yCoord * 2.0 : targetVec3.yCoord;
               if (d0 >= 0.1) {
                  double d1 = 0.1;
                  double d8;
                  double d9;
                  double d10;
                  if (flag1) {
                     d8 = d0;
                     d9 = d1;
                     d10 = 0.8;
                  } else {
                     d8 = d0;
                     d9 = d1;
                     d10 = 0.45;
                  }

                  if (d8 < d9 + d10 && this.contents[1] != null) {
                     b1 = 1;
                     break label141;
                  }
               }

               double d2 = 0.9;
               double d5;
               double d6;
               double d7;
               if (flag1) {
                  d5 = d0;
                  d6 = d2;
                  d7 = 0.3;
               } else {
                  d5 = d0;
                  d6 = d2;
                  d7 = 0.0;
               }

               if (d5 >= d6 + d7) {
                  double d3 = 0.9;
                  if (flag1) {
                     d5 = d0;
                     d6 = d3;
                     d7 = 1.0;
                  } else {
                     d5 = d0;
                     d6 = d3;
                     d7 = 0.7;
                  }

                  if (d5 < d6 + d7 && this.contents[3] != null) {
                     b1 = 3;
                     break label141;
                  }
               }

               if (d0 >= 0.4) {
                  double d4 = 0.4;
                  if (flag1) {
                     d5 = d0;
                     d6 = d4;
                     d7 = 1.0;
                  } else {
                     d5 = d0;
                     d6 = d4;
                     d7 = 0.8;
                  }

                  if (d5 < d6 + d7 && this.contents[2] != null) {
                     b1 = 2;
                     break label141;
                  }
               }

               if (d0 >= 1.6 && this.contents[4] != null) {
                  b1 = 4;
               }
            }

            boolean flag2 = this.contents[b1] != null;
            if ((this.disabledSlots & 1 << b1) != 0 || (this.disabledSlots & 1 << b0) != 0) {
               b1 = b0;
               if ((this.disabledSlots & 1 << b0) != 0) {
                  if ((this.disabledSlots & 1) != 0) {
                     return true;
                  }

                  b1 = 0;
               }
            }

            if (flag && b0 == 0 && !this.getShowArms()) {
               return true;
            } else {
               if (flag) {
                  this.func_175422_a(player, b0);
               } else if (flag2) {
                  this.func_175422_a(player, b1);
               }

               return true;
            }
         } else {
            return true;
         }
      }
   }

   private void func_175422_a(EntityPlayer entityPlayer, int i) {
      ItemStack itemstack = this.contents[i];
      if (itemstack == null || (this.disabledSlots & 1 << i + 8) == 0) {
         if (itemstack == null) {
            int j = this.disabledSlots & 1 << i + 16;
            if (j != 0) {
               return;
            }
         }

         int i1 = entityPlayer.inventory.currentItem;
         ItemStack itemstack1 = entityPlayer.inventory.getStackInSlot(i1);
         if (entityPlayer.capabilities.isCreativeMode && (itemstack == null || itemstack.getItem() == Item.getItemFromBlock(Blocks.air)) && itemstack1 != null) {
            ItemStack itemstack3 = itemstack1.copy();
            itemstack3.stackSize = 1;
            this.setCurrentItemOrArmor(i, itemstack3);
         } else if (itemstack1 == null || itemstack1.stackSize <= 1) {
            this.setCurrentItemOrArmor(i, itemstack1);
            entityPlayer.inventory.setInventorySlotContents(i1, itemstack);
         } else if (itemstack == null) {
            ItemStack itemstack2 = itemstack1.copy();
            itemstack2.stackSize = 1;
            this.setCurrentItemOrArmor(i, itemstack2);
            itemstack1.stackSize--;
         }
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      boolean flag2 = super.worldObj.isRemote;
      if (flag2) {
         return false;
      } else {
         boolean flag3 = DamageSource.outOfWorld.equals(source);
         if (flag3) {
            this.setDead();
            return false;
         } else {
            boolean flag4 = this.isEntityInvulnerable(source);
            if (flag4 || this.canInteract || this.hasMarker()) {
               return false;
            } else if (source.isExplosion()) {
               this.dropContents();
               this.setDead();
               return false;
            } else if (DamageSource.inFire.equals(source)) {
               if (!this.isBurning()) {
                  this.setFire(5);
               } else {
                  this.damageArmorStand(0.15F);
               }

               return false;
            } else if (DamageSource.onFire.equals(source) && this.getHealth() > 0.5F) {
               this.damageArmorStand(4.0F);
               return false;
            } else {
               boolean flag = "arrow".equals(source.getDamageType());
               boolean flag1 = "player".equals(source.getDamageType());
               if (!flag1 && !flag) {
                  return false;
               } else {
                  if (source.getSourceOfDamage() instanceof EntityArrow) {
                     source.getSourceOfDamage().setDead();
                  }

                  if (source.getEntity() instanceof EntityPlayer && !((EntityPlayer)source.getEntity()).capabilities.allowEdit) {
                     return false;
                  } else if (source.isCreativePlayer()) {
                     this.playParticles();
                     this.setDead();
                     return false;
                  } else {
                     long i = super.worldObj.getTotalWorldTime();
                     if (i - this.punchCooldown > 5L && !flag) {
                        this.punchCooldown = i;
                     } else {
                        this.dropBlock();
                        this.playParticles();
                        this.setDead();
                     }

                     return false;
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
      if (Double.isNaN(d0) || d0 == 0.0) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      double d1;
      int i = (d1 = distance - d0 * d0) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
      return i < 0;
   }

   private void playParticles() {
      if (super.worldObj instanceof WorldServer) {
         ((WorldServer)super.worldObj)
            .spawnParticle(
               EnumParticleTypes.BLOCK_DUST,
               super.posX,
               super.posY + (double)super.height / 1.5,
               super.posZ,
               10,
               (double)(super.width / 4.0F),
               (double)(super.height / 4.0F),
               (double)(super.width / 4.0F),
               0.05,
               Block.getStateId(Blocks.planks.getDefaultState())
            );
      }
   }

   private void damageArmorStand(float f) {
      float f1 = this.getHealth();
      f1 -= f;
      if (f1 <= 0.5F) {
         this.dropContents();
         this.setDead();
      } else {
         this.setHealth(f1);
      }
   }

   private void dropBlock() {
      Block.spawnAsEntity(super.worldObj, new BlockPos(this), new ItemStack(Items.armor_stand));
      this.dropContents();
   }

   private void dropContents() {
      for (int i = 0; i < this.contents.length; i++) {
         ItemStack itemstack = this.contents[i];
         if (itemstack != null) {
            ItemStack itemstack1 = this.contents[i];
            if (itemstack1.stackSize > 0) {
               if (this.contents[i] != null) {
                  Block.spawnAsEntity(super.worldObj, new BlockPos(this).up(), this.contents[i]);
               }

               this.contents[i] = null;
            }
         }
      }
   }

   @Override
   public float updateDistance(float f, float g) {
      super.prevRenderYawOffset = super.prevRotationYaw;
      super.renderYawOffset = super.rotationYaw;
      return 0.0F;
   }

   @Override
   public float getEyeHeight() {
      return this.isChild() ? super.height * 0.5F : super.height * 0.9F;
   }

   @Override
   public void moveEntityWithHeading(float strafe, float forward) {
      if (!this.hasNoGravity()) {
         super.moveEntityWithHeading(strafe, forward);
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      Rotations rotations = super.dataWatcher.getWatchableObjectRotations(11);
      Rotations rotations6 = this.headRotation;
      if (!rotations6.equals(rotations)) {
         this.setHeadRotation(rotations);
      }

      Rotations rotations1 = super.dataWatcher.getWatchableObjectRotations(12);
      Rotations rotations7 = this.bodyRotation;
      if (!rotations7.equals(rotations1)) {
         this.setBodyRotation(rotations1);
      }

      Rotations rotations2 = super.dataWatcher.getWatchableObjectRotations(13);
      Rotations rotations8 = this.leftArmRotation;
      if (!rotations8.equals(rotations2)) {
         this.setLeftArmRotation(rotations2);
      }

      Rotations rotations3 = super.dataWatcher.getWatchableObjectRotations(14);
      Rotations rotations9 = this.rightArmRotation;
      if (!rotations9.equals(rotations3)) {
         this.setRightArmRotation(rotations3);
      }

      Rotations rotations4 = super.dataWatcher.getWatchableObjectRotations(15);
      Rotations rotations10 = this.leftLegRotation;
      if (!rotations10.equals(rotations4)) {
         this.setLeftLegRotation(rotations4);
      }

      Rotations rotations5 = super.dataWatcher.getWatchableObjectRotations(16);
      boolean flag1 = this.rightLegRotation.equals(rotations5);
      if (!flag1) {
         this.setRightLegRotation(rotations5);
      }

      boolean flag = this.hasMarker();
      boolean flag2 = this.field_181028_bj;
      if (!flag2 && flag) {
         this.func_181550_a(false);
      } else {
         boolean flag3 = this.field_181028_bj;
         if (!flag3 || flag) {
            return;
         }

         this.func_181550_a(true);
      }

      this.field_181028_bj = flag;
   }

   private void func_181550_a(boolean bl) {
      double d0 = super.posX;
      double d1 = super.posY;
      double d2 = super.posZ;
      if (bl) {
         this.setSize(0.5F, 1.975F);
      } else {
         this.setSize(0.0F, 0.0F);
      }

      this.setPosition(d0, d1, d2);
   }

   @Override
   public void updatePotionMetadata() {
      this.setInvisible(this.canInteract);
   }

   @Override
   public void setInvisible(boolean invisible) {
      this.canInteract = invisible;
      super.setInvisible(invisible);
   }

   @Override
   public boolean isChild() {
      return this.isSmall();
   }

   @Override
   public void onKillCommand() {
      this.setDead();
   }

   @Override
   public boolean isImmuneToExplosions() {
      return this.isInvisible();
   }

   private void setSmall(boolean bl) {
      byte b0 = super.dataWatcher.getWatchableObjectByte(10);
      if (bl) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 = (byte)(b0 & -2);
      }

      super.dataWatcher.updateObject(10, b0);
   }

   public boolean isSmall() {
      return (super.dataWatcher.getWatchableObjectByte(10) & 1) != 0;
   }

   private void setNoGravity(boolean bl) {
      byte b0 = super.dataWatcher.getWatchableObjectByte(10);
      if (bl) {
         b0 = (byte)(b0 | 2);
      } else {
         b0 = (byte)(b0 & -3);
      }

      super.dataWatcher.updateObject(10, b0);
   }

   public boolean hasNoGravity() {
      return (super.dataWatcher.getWatchableObjectByte(10) & 2) != 0;
   }

   private void setShowArms(boolean bl) {
      byte b0 = super.dataWatcher.getWatchableObjectByte(10);
      if (bl) {
         b0 = (byte)(b0 | 4);
      } else {
         b0 = (byte)(b0 & -5);
      }

      super.dataWatcher.updateObject(10, b0);
   }

   public boolean getShowArms() {
      return (super.dataWatcher.getWatchableObjectByte(10) & 4) != 0;
   }

   private void setNoBasePlate(boolean bl) {
      byte b0 = super.dataWatcher.getWatchableObjectByte(10);
      if (bl) {
         b0 = (byte)(b0 | 8);
      } else {
         b0 = (byte)(b0 & -9);
      }

      super.dataWatcher.updateObject(10, b0);
   }

   public boolean hasNoBasePlate() {
      return (super.dataWatcher.getWatchableObjectByte(10) & 8) != 0;
   }

   private void setMarker(boolean bl) {
      byte b0 = super.dataWatcher.getWatchableObjectByte(10);
      if (bl) {
         b0 = (byte)(b0 | 16);
      } else {
         b0 = (byte)(b0 & -17);
      }

      super.dataWatcher.updateObject(10, b0);
   }

   public boolean hasMarker() {
      return (super.dataWatcher.getWatchableObjectByte(10) & 16) != 0;
   }

   public void setHeadRotation(Rotations rotations) {
      this.headRotation = rotations;
      super.dataWatcher.updateObject(11, rotations);
   }

   public void setBodyRotation(Rotations rotations) {
      this.bodyRotation = rotations;
      super.dataWatcher.updateObject(12, rotations);
   }

   public void setLeftArmRotation(Rotations rotations) {
      this.leftArmRotation = rotations;
      super.dataWatcher.updateObject(13, rotations);
   }

   public void setRightArmRotation(Rotations rotations) {
      this.rightArmRotation = rotations;
      super.dataWatcher.updateObject(14, rotations);
   }

   public void setLeftLegRotation(Rotations rotations) {
      this.leftLegRotation = rotations;
      super.dataWatcher.updateObject(15, rotations);
   }

   public void setRightLegRotation(Rotations rotations) {
      this.rightLegRotation = rotations;
      super.dataWatcher.updateObject(16, rotations);
   }

   public Rotations getHeadRotation() {
      return this.headRotation;
   }

   public Rotations getBodyRotation() {
      return this.bodyRotation;
   }

   public Rotations getLeftArmRotation() {
      return this.leftArmRotation;
   }

   public Rotations getRightArmRotation() {
      return this.rightArmRotation;
   }

   public Rotations getLeftLegRotation() {
      return this.leftLegRotation;
   }

   public Rotations getRightLegRotation() {
      return this.rightLegRotation;
   }

   @Override
   public boolean canBeCollidedWith() {
      boolean flag = super.canBeCollidedWith();
      if (flag) {
         boolean flag1 = this.hasMarker();
         if (!flag1) {
            return true;
         }
      }

      return false;
   }
}
