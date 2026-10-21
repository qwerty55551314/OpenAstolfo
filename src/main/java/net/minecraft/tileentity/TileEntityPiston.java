package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityPiston extends TileEntity implements ITickable {
   private IBlockState pistonState;
   private EnumFacing pistonFacing;
   private boolean extending;
   private boolean shouldHeadBeRendered;
   private float progress;
   private float lastProgress;
   private List<Entity> field_174933_k = Lists.newArrayList();

   public TileEntityPiston() {
   }

   public TileEntityPiston(IBlockState pistonStateIn, EnumFacing pistonFacingIn, boolean extendingIn, boolean shouldHeadBeRenderedIn) {
      this.pistonState = pistonStateIn;
      this.pistonFacing = pistonFacingIn;
      this.extending = extendingIn;
      this.shouldHeadBeRendered = shouldHeadBeRenderedIn;
   }

   public IBlockState getPistonState() {
      return this.pistonState;
   }

   @Override
   public int getBlockMetadata() {
      return 0;
   }

   public boolean isExtending() {
      return this.extending;
   }

   public EnumFacing getFacing() {
      return this.pistonFacing;
   }

   public boolean shouldPistonHeadBeRendered() {
      return this.shouldHeadBeRendered;
   }

   public float getProgress(float ticks) {
      if (ticks > 1.0F) {
         ticks = 1.0F;
      }

      return this.lastProgress + (this.progress - this.lastProgress) * ticks;
   }

   public float getOffsetX(float ticks) {
      return this.extending
         ? (this.getProgress(ticks) - 1.0F) * (float)this.pistonFacing.getFrontOffsetX()
         : (1.0F - this.getProgress(ticks)) * (float)this.pistonFacing.getFrontOffsetX();
   }

   public float getOffsetY(float ticks) {
      return this.extending
         ? (this.getProgress(ticks) - 1.0F) * (float)this.pistonFacing.getFrontOffsetY()
         : (1.0F - this.getProgress(ticks)) * (float)this.pistonFacing.getFrontOffsetY();
   }

   public float getOffsetZ(float ticks) {
      return this.extending
         ? (this.getProgress(ticks) - 1.0F) * (float)this.pistonFacing.getFrontOffsetZ()
         : (1.0F - this.getProgress(ticks)) * (float)this.pistonFacing.getFrontOffsetZ();
   }

   private void launchWithSlimeBlock(float f, float g) {
      if (this.extending) {
         f = 1.0F - f;
      } else {
         f--;
      }

      AxisAlignedBB axisalignedbb = Blocks.piston_extension.getBoundingBox(super.worldObj, super.pos, this.pistonState, f, this.pistonFacing);
      if (axisalignedbb != null) {
         List list = super.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
         boolean flag = list.isEmpty();
         if (!flag) {
            this.field_174933_k.addAll(list);

            for (Entity entity : this.field_174933_k) {
               if (this.pistonState.getBlock() == Blocks.slime_block && this.extending) {
                  switch (this.pistonFacing.getAxis()) {
                     case X:
                        entity.motionX = (double)this.pistonFacing.getFrontOffsetX();
                        break;
                     case Y:
                        entity.motionY = (double)this.pistonFacing.getFrontOffsetY();
                        break;
                     case Z:
                        entity.motionZ = (double)this.pistonFacing.getFrontOffsetZ();
                  }
               } else {
                  entity.moveEntity(
                     (double)(g * (float)this.pistonFacing.getFrontOffsetX()),
                     (double)(g * (float)this.pistonFacing.getFrontOffsetY()),
                     (double)(g * (float)this.pistonFacing.getFrontOffsetZ())
                  );
               }
            }

            this.field_174933_k.clear();
         }
      }
   }

   public void clearPistonTileEntity() {
      if (this.lastProgress < 1.0F) {
         World world = super.worldObj;
         if (world != null) {
            this.lastProgress = this.progress = 1.0F;
            super.worldObj.removeTileEntity(super.pos);
            this.invalidate();
            BlockPos blockpos = super.pos;
            World world1 = super.worldObj;
            if (world1.getBlockState(blockpos).getBlock() == Blocks.piston_extension) {
               super.worldObj.setBlockState(super.pos, this.pistonState, 3);
               super.worldObj.notifyBlockOfStateChange(super.pos, this.pistonState.getBlock());
            }
         }
      }
   }

   @Override
   public void update() {
      this.lastProgress = this.progress;
      float f;
      int i = (f = this.lastProgress - 1.0F) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
      if (i >= 0) {
         this.launchWithSlimeBlock(1.0F, 0.25F);
         super.worldObj.removeTileEntity(super.pos);
         this.invalidate();
         if (super.worldObj.getBlockState(super.pos).getBlock() == Blocks.piston_extension) {
            super.worldObj.setBlockState(super.pos, this.pistonState, 3);
            super.worldObj.notifyBlockOfStateChange(super.pos, this.pistonState.getBlock());
         }
      } else {
         this.progress += 0.5F;
         float f1;
         int j = (f1 = this.progress - 1.0F) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
         if (j >= 0) {
            this.progress = 1.0F;
         }

         if (this.extending) {
            this.launchWithSlimeBlock(this.progress, this.progress - this.lastProgress + 0.0625F);
         }
      }
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.pistonState = Block.getBlockById(compound.getInteger("blockId")).getStateFromMeta(compound.getInteger("blockData"));
      this.pistonFacing = EnumFacing.getFront(compound.getInteger("facing"));
      this.lastProgress = this.progress = compound.getFloat("progress");
      this.extending = compound.getBoolean("extending");
   }

   @Override
   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
      compound.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
      compound.setInteger("facing", this.pistonFacing.getIndex());
      compound.setFloat("progress", this.lastProgress);
      compound.setBoolean("extending", this.extending);
   }
}
