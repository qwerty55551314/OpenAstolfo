package net.minecraft.tileentity;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityNote extends TileEntity {
   public byte note;
   public boolean previousRedstoneState;

   @Override
   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setByte("note", this.note);
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.note = compound.getByte("note");
      this.note = (byte)MathHelper.clamp_int(this.note, 0, 24);
   }

   public void changePitch() {
      this.note = (byte)((this.note + 1) % 25);
      this.markDirty();
   }

   public void triggerNote(World worldIn, BlockPos blockPos) {
      Material material1 = worldIn.getBlockState(blockPos.up()).getBlock().getMaterial();
      if (material1 == Material.air) {
         Material material = worldIn.getBlockState(blockPos.down()).getBlock().getMaterial();
         int b0 = 0;
         Material material2 = Material.rock;
         if (material == material2) {
            b0 = 1;
         }

         Material material3 = Material.sand;
         if (material == material3) {
            b0 = 2;
         }

         Material material4 = Material.glass;
         if (material == material4) {
            b0 = 3;
         }

         if (material == Material.wood) {
            b0 = 4;
         }

         worldIn.addBlockEvent(blockPos, Blocks.noteblock, b0, this.note);
      }
   }
}
