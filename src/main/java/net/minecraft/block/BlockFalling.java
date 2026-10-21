package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockFalling extends Block {
   public static boolean fallInstantly;
   private static final long m = 5011311478116974624L;

   public BlockFalling() {
      super(Material.sand);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public BlockFalling(Material materialIn) {
      super(materialIn);
   }

   @Override
   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
   }

   @Override
   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
      worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
   }

   @Override
   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (!worldIn.isRemote) {
         this.checkFallable(worldIn, pos);
      }
   }

   private void checkFallable(World worldIn, BlockPos pos) {
      boolean flag = canFallInto(worldIn, pos.down());
      if (flag) {
         int j = pos.getY();
         if (j >= 0) {
            int i = (int)m;
            boolean flag1 = fallInstantly;
            if (flag1 || !worldIn.isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i))) {
               worldIn.setBlockToAir(pos);

               BlockPos blockpos;
               for (blockpos = pos.down(); canFallInto(worldIn, blockpos); blockpos = blockpos.down()) {
                  int k = blockpos.getY();
                  if (k <= 0) {
                     break;
                  }
               }

               int l = blockpos.getY();
               if (l > 0) {
                  worldIn.setBlockState(blockpos.up(), this.getDefaultState());
               }
            } else if (!worldIn.isRemote) {
               EntityFallingBlock entityfallingblock = new EntityFallingBlock(
                  worldIn, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, worldIn.getBlockState(pos)
               );
               this.onStartFalling(entityfallingblock);
               worldIn.spawnEntityInWorld(entityfallingblock);
            }
         }
      }
   }

   public void onStartFalling(EntityFallingBlock fallingEntity) {
   }

   @Override
   public int tickRate(World worldIn) {
      return 2;
   }

   public static boolean canFallInto(World worldIn, BlockPos pos) {
      Block block = worldIn.getBlockState(pos).getBlock();
      Material material = block.blockMaterial;
      if (block != Blocks.fire) {
         Material material1 = Material.air;
         if (material != material1) {
            Material material2 = Material.water;
            if (material != material2 && material != Material.lava) {
               return false;
            }
         }
      }

      return true;
   }

   public void onEndFalling(World worldIn, BlockPos pos) {
   }
}
