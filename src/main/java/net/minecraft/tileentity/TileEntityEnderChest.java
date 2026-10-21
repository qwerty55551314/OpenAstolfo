package net.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;

public class TileEntityEnderChest extends TileEntity implements ITickable {
   public float lidAngle;
   public float prevLidAngle;
   public int numPlayersUsing;
   private int ticksSinceSync;

   @Override
   public void update() {
      int l = ++this.ticksSinceSync % 20 * 4;
      if (l == 0) {
         super.worldObj.addBlockEvent(super.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
      }

      this.prevLidAngle = this.lidAngle;
      int i = super.pos.getX();
      int j = super.pos.getY();
      int k = super.pos.getZ();
      float f = 0.1F;
      int i1 = this.numPlayersUsing;
      if (i1 > 0 && this.lidAngle == 0.0F) {
         double d0 = (double)i + 0.5;
         double d1 = (double)k + 0.5;
         super.worldObj.playSoundEffect(d0, (double)j + 0.5, d1, "random.chestopen", 0.5F, super.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      int j1 = this.numPlayersUsing;
      if (j1 != 0 || !(this.lidAngle > 0.0F)) {
         int k1 = this.numPlayersUsing;
         if (k1 <= 0 || !(this.lidAngle < 1.0F)) {
            return;
         }
      }

      float f3 = this.lidAngle;
      if (this.numPlayersUsing > 0) {
         this.lidAngle += f;
      } else {
         this.lidAngle -= f;
      }

      float f2 = this.lidAngle;
      if (f2 > 1.0F) {
         this.lidAngle = 1.0F;
      }

      float f1 = 0.5F;
      float f4;
      int l1 = (f4 = this.lidAngle - f1) == 0.0F ? 0 : (f4 < 0.0F ? -1 : 1);
      if (l1 < 0 && f3 >= f1) {
         double d3 = (double)i + 0.5;
         double d2 = (double)k + 0.5;
         super.worldObj.playSoundEffect(d3, (double)j + 0.5, d2, "random.chestclosed", 0.5F, super.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      if (this.lidAngle < 0.0F) {
         this.lidAngle = 0.0F;
      }
   }

   @Override
   public boolean receiveClientEvent(int id, int typeIn) {
      if (id == 1) {
         this.numPlayersUsing = typeIn;
         return true;
      } else {
         return super.receiveClientEvent(id, typeIn);
      }
   }

   @Override
   public void invalidate() {
      this.updateContainingBlockInfo();
      super.invalidate();
   }

   public void openChest() {
      this.numPlayersUsing++;
      super.worldObj.addBlockEvent(super.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
   }

   public void closeChest() {
      this.numPlayersUsing--;
      super.worldObj.addBlockEvent(super.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
   }

   public boolean canBeUsed(EntityPlayer entityPlayer) {
      return super.worldObj.getTileEntity(super.pos) != this
         ? false
         : entityPlayer.getDistanceSq((double)super.pos.getX() + 0.5, (double)super.pos.getY() + 0.5, (double)super.pos.getZ() + 0.5) <= 64.0;
   }
}
