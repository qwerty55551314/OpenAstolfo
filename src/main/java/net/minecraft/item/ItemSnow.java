package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSnow extends ItemBlock {
   private static final long d = -6940690476303384569L;

   public ItemSnow(Block block) {
      super(block);
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   @Override
   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
      int j = stack.stackSize;
      if (j == 0) {
         return false;
      } else {
         boolean flag = playerIn.canPlayerEdit(pos, side, stack);
         if (!flag) {
            return false;
         } else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            BlockPos blockpos = pos;
            if ((side != EnumFacing.UP || block != super.block) && !block.isReplaceable(worldIn, pos)) {
               blockpos = pos.offset(side);
               iblockstate = worldIn.getBlockState(blockpos);
               block = iblockstate.getBlock();
            }

            if (block == super.block) {
               int i = iblockstate.getValue(BlockSnow.LAYERS);
               if (i <= (int)d) {
                  IBlockState iblockstate1 = iblockstate.withProperty(BlockSnow.LAYERS, i + 1);
                  AxisAlignedBB axisalignedbb = super.block.getCollisionBoundingBox(worldIn, blockpos, iblockstate1);
                  if (axisalignedbb != null && worldIn.checkNoEntityCollision(axisalignedbb) && worldIn.setBlockState(blockpos, iblockstate1, 2)) {
                     worldIn.playSoundEffect(
                        (double)((float)blockpos.getX() + 0.5F),
                        (double)((float)blockpos.getY() + 0.5F),
                        (double)((float)blockpos.getZ() + 0.5F),
                        super.block.stepSound.getPlaceSound(),
                        (super.block.stepSound.getVolume() + 1.0F) / 2.0F,
                        super.block.stepSound.getFrequency() * 0.8F
                     );
                     stack.stackSize--;
                     return true;
                  }
               }
            }

            return super.onItemUse(stack, playerIn, worldIn, blockpos, side, hitX, hitY, hitZ);
         }
      }
   }

   @Override
   public int getMetadata(int damage) {
      return damage;
   }
}
