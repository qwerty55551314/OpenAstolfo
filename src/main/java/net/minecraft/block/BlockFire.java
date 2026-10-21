package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class BlockFire extends Block {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
   public static final PropertyBool FLIP = PropertyBool.create("flip");
   public static final PropertyBool ALT = PropertyBool.create("alt");
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyInteger UPPER = PropertyInteger.create("upper", 0, 2);
   private final Map<Block, Integer> encouragements = Maps.newIdentityHashMap();
   private final Map<Block, Integer> flammabilities = Maps.newIdentityHashMap();

   @Override
   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      int i = pos.getX();
      int j = pos.getY();
      int k = pos.getZ();
      boolean flag2 = World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
      if (!flag2 && !Blocks.fire.canCatchFire(worldIn, pos.down())) {
         int i1 = i + j + k & 1;
         boolean flag = i1 == 1;
         int j1 = i / 2 + j / 2 + k / 2 & 1;
         boolean flag1 = j1 == 1;
         int l = 0;
         boolean flag3 = this.canCatchFire(worldIn, pos.up());
         if (flag3) {
            l = flag ? 1 : 2;
         }

         return state.withProperty(NORTH, this.canCatchFire(worldIn, pos.north()))
            .withProperty(EAST, this.canCatchFire(worldIn, pos.east()))
            .withProperty(SOUTH, this.canCatchFire(worldIn, pos.south()))
            .withProperty(WEST, this.canCatchFire(worldIn, pos.west()))
            .withProperty(UPPER, l)
            .withProperty(FLIP, flag1)
            .withProperty(ALT, flag);
      } else {
         return this.getDefaultState();
      }
   }

   public BlockFire() {
      super(Material.fire);
      this.setDefaultState(
         super.blockState
            .getBaseState()
            .withProperty(AGE, 0)
            .withProperty(FLIP, false)
            .withProperty(ALT, false)
            .withProperty(NORTH, false)
            .withProperty(EAST, false)
            .withProperty(SOUTH, false)
            .withProperty(WEST, false)
            .withProperty(UPPER, 0)
      );
      this.setTickRandomly(true);
   }

   public static void init() {
      Blocks.fire.setFireInfo(Blocks.planks, 5, 20);
      Blocks.fire.setFireInfo(Blocks.double_wooden_slab, 5, 20);
      Blocks.fire.setFireInfo(Blocks.wooden_slab, 5, 20);
      Blocks.fire.setFireInfo(Blocks.oak_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.spruce_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.birch_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.jungle_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.dark_oak_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.acacia_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.oak_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.spruce_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.birch_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.jungle_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.dark_oak_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.acacia_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.oak_stairs, 5, 20);
      Blocks.fire.setFireInfo(Blocks.birch_stairs, 5, 20);
      Blocks.fire.setFireInfo(Blocks.spruce_stairs, 5, 20);
      Blocks.fire.setFireInfo(Blocks.jungle_stairs, 5, 20);
      Blocks.fire.setFireInfo(Blocks.log, 5, 5);
      Blocks.fire.setFireInfo(Blocks.log2, 5, 5);
      Blocks.fire.setFireInfo(Blocks.leaves, 30, 60);
      Blocks.fire.setFireInfo(Blocks.leaves2, 30, 60);
      Blocks.fire.setFireInfo(Blocks.bookshelf, 30, 20);
      Blocks.fire.setFireInfo(Blocks.tnt, 15, 100);
      Blocks.fire.setFireInfo(Blocks.tallgrass, 60, 100);
      Blocks.fire.setFireInfo(Blocks.double_plant, 60, 100);
      Blocks.fire.setFireInfo(Blocks.yellow_flower, 60, 100);
      Blocks.fire.setFireInfo(Blocks.red_flower, 60, 100);
      Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
      Blocks.fire.setFireInfo(Blocks.wool, 30, 60);
      Blocks.fire.setFireInfo(Blocks.vine, 15, 100);
      Blocks.fire.setFireInfo(Blocks.coal_block, 5, 5);
      Blocks.fire.setFireInfo(Blocks.hay_block, 60, 20);
      Blocks.fire.setFireInfo(Blocks.carpet, 60, 20);
   }

   public void setFireInfo(Block blockIn, int encouragement, int flammability) {
      this.encouragements.put(blockIn, encouragement);
      this.flammabilities.put(blockIn, flammability);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
      return null;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public int quantityDropped(Random randomIn) {
      return 0;
   }

   @Override
   public int tickRate(World worldIn) {
      return 30;
   }

   @Override
   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      boolean flag2 = worldIn.getGameRules().getBoolean("doFireTick");
      if (flag2) {
         if (!this.canPlaceBlockAt(worldIn, pos)) {
            worldIn.setBlockToAir(pos);
         }

         Block block = worldIn.getBlockState(pos.down()).getBlock();
         boolean flag = block == Blocks.netherrack;
         boolean flag3 = worldIn.provider instanceof WorldProviderEnd;
         if (flag3 && block == Blocks.bedrock) {
            flag = true;
         }

         if (!flag && worldIn.isRaining() && this.canDie(worldIn, pos)) {
            worldIn.setBlockToAir(pos);
         } else {
            int i = state.getValue(AGE);
            if (i < 15) {
               state = state.withProperty(AGE, i + rand.nextInt(3) / 2);
               worldIn.setBlockState(pos, state, 4);
            }

            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(10));
            if (!flag) {
               if (!this.canNeighborCatchFire(worldIn, pos)) {
                  if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) || i > 3) {
                     worldIn.setBlockToAir(pos);
                  }

                  return;
               }

               if (!this.canCatchFire(worldIn, pos.down()) && i == 15 && rand.nextInt(4) == 0) {
                  worldIn.setBlockToAir(pos);
                  return;
               }
            }

            boolean flag1 = worldIn.isBlockinHighHumidity(pos);
            int b0 = 0;
            if (flag1) {
               b0 = -50;
            }

            this.catchOnFire(worldIn, pos.east(), 300 + b0, rand, i);
            this.catchOnFire(worldIn, pos.west(), 300 + b0, rand, i);
            this.catchOnFire(worldIn, pos.down(), 250 + b0, rand, i);
            this.catchOnFire(worldIn, pos.up(), 250 + b0, rand, i);
            this.catchOnFire(worldIn, pos.north(), 300 + b0, rand, i);
            this.catchOnFire(worldIn, pos.south(), 300 + b0, rand, i);

            for (int j = -1; j <= 1; j++) {
               for (int k = -1; k <= 1; k++) {
                  for (int l = -1; l <= 4; l++) {
                     if (j != 0 || l != 0 || k != 0) {
                        int i1 = 100;
                        if (l > 1) {
                           i1 += (l - 1) * 100;
                        }

                        BlockPos blockpos = pos.add(j, l, k);
                        int j1 = this.getNeighborEncouragement(worldIn, blockpos);
                        if (j1 > 0) {
                           int k1 = (j1 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (i + 30);
                           if (flag1) {
                              k1 /= 2;
                           }

                           if (k1 > 0 && rand.nextInt(i1) <= k1 && (!worldIn.isRaining() || !this.canDie(worldIn, blockpos))) {
                              int l1 = i + rand.nextInt(5) / 4;
                              if (l1 > 15) {
                                 l1 = 15;
                              }

                              worldIn.setBlockState(blockpos, state.withProperty(AGE, l1), 3);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public boolean canDie(World worldIn, BlockPos pos) {
      return worldIn.isRainingAt(pos)
         || worldIn.isRainingAt(pos.west())
         || worldIn.isRainingAt(pos.east())
         || worldIn.isRainingAt(pos.north())
         || worldIn.isRainingAt(pos.south());
   }

   @Override
   public boolean requiresUpdates() {
      return false;
   }

   private int getFlammability(Block blockIn) {
      Integer integer = this.flammabilities.get(blockIn);
      return integer == null ? 0 : integer;
   }

   private int getEncouragement(Block blockIn) {
      Integer integer = this.encouragements.get(blockIn);
      return integer == null ? 0 : integer;
   }

   private void catchOnFire(World worldIn, BlockPos pos, int chance, Random randomIn, int age) {
      int i = this.getFlammability(worldIn.getBlockState(pos).getBlock());
      if (randomIn.nextInt(chance) < i) {
         IBlockState iblockstate = worldIn.getBlockState(pos);
         int k = randomIn.nextInt(age + 10);
         if (k < 5 && !worldIn.isRainingAt(pos)) {
            int j = age + randomIn.nextInt(5) / 4;
            if (j > 15) {
               j = 15;
            }

            worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, j), 3);
         } else {
            worldIn.setBlockToAir(pos);
         }

         Block block = iblockstate.getBlock();
         if (block == Blocks.tnt) {
            Blocks.tnt.onBlockDestroyedByPlayer(worldIn, pos, iblockstate.withProperty(BlockTNT.EXPLODE, true));
         }
      }
   }

   private boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
      for (EnumFacing enumfacing : EnumFacing.values()) {
         if (this.canCatchFire(worldIn, pos.offset(enumfacing))) {
            return true;
         }
      }

      return false;
   }

   private int getNeighborEncouragement(World worldIn, BlockPos pos) {
      boolean flag = worldIn.isAirBlock(pos);
      if (!flag) {
         return 0;
      } else {
         int i = 0;

         for (EnumFacing enumfacing : EnumFacing.values()) {
            int j = Math.max(this.getEncouragement(worldIn.getBlockState(pos.offset(enumfacing)).getBlock()), i);
            i = j;
         }

         return i;
      }
   }

   @Override
   public boolean isCollidable() {
      return false;
   }

   public boolean canCatchFire(IBlockAccess worldIn, BlockPos pos) {
      return this.getEncouragement(worldIn.getBlockState(pos).getBlock()) > 0;
   }

   @Override
   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) || this.canNeighborCatchFire(worldIn, pos);
   }

   @Override
   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
      boolean flag = World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
      if (!flag) {
         boolean flag1 = this.canNeighborCatchFire(worldIn, pos);
         if (!flag1) {
            worldIn.setBlockToAir(pos);
         }
      }
   }

   @Override
   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      int i = worldIn.provider.getDimensionId();
      if (i > 0 || !Blocks.portal.func_176548_d(worldIn, pos)) {
         if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.down())) {
            boolean flag = this.canNeighborCatchFire(worldIn, pos);
            if (!flag) {
               worldIn.setBlockToAir(pos);
               return;
            }
         }

         worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + worldIn.rand.nextInt(10));
      }
   }

   @Override
   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      int j = rand.nextInt(24);
      if (j == 0) {
         worldIn.playSound(
            (double)((float)pos.getX() + 0.5F),
            (double)((float)pos.getY() + 0.5F),
            (double)((float)pos.getZ() + 0.5F),
            "fire.fire",
            1.0F + rand.nextFloat(),
            rand.nextFloat() * 0.7F + 0.3F,
            false
         );
      }

      boolean flag = World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
      if (!flag && !Blocks.fire.canCatchFire(worldIn, pos.down())) {
         if (Blocks.fire.canCatchFire(worldIn, pos.west())) {
            for (int k = 0; k < 2; k++) {
               double d3 = (double)pos.getX() + rand.nextDouble() * 0.1F;
               double d8 = (double)pos.getY() + rand.nextDouble();
               double d13 = (double)pos.getZ() + rand.nextDouble();
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d8, d13, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.fire.canCatchFire(worldIn, pos.east())) {
            for (int l = 0; l < 2; l++) {
               double d4 = (double)(pos.getX() + 1) - rand.nextDouble() * 0.1F;
               double d9 = (double)pos.getY() + rand.nextDouble();
               double d14 = (double)pos.getZ() + rand.nextDouble();
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d4, d9, d14, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.fire.canCatchFire(worldIn, pos.north())) {
            for (int i1 = 0; i1 < 2; i1++) {
               double d5 = (double)pos.getX() + rand.nextDouble();
               double d10 = (double)pos.getY() + rand.nextDouble();
               double d15 = (double)pos.getZ() + rand.nextDouble() * 0.1F;
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d5, d10, d15, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.fire.canCatchFire(worldIn, pos.south())) {
            for (int j1 = 0; j1 < 2; j1++) {
               double d6 = (double)pos.getX() + rand.nextDouble();
               double d11 = (double)pos.getY() + rand.nextDouble();
               double d16 = (double)(pos.getZ() + 1) - rand.nextDouble() * 0.1F;
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d6, d11, d16, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.fire.canCatchFire(worldIn, pos.up())) {
            for (int k1 = 0; k1 < 2; k1++) {
               double d7 = (double)pos.getX() + rand.nextDouble();
               double d12 = (double)(pos.getY() + 1) - rand.nextDouble() * 0.1F;
               double d17 = (double)pos.getZ() + rand.nextDouble();
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0, 0.0, 0.0);
            }
         }
      } else {
         for (int i = 0; i < 3; i++) {
            double d0 = (double)pos.getX() + rand.nextDouble();
            double d1 = (double)pos.getY() + rand.nextDouble() * 0.5 + 0.5;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   public MapColor getMapColor(IBlockState state) {
      return MapColor.tntColor;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(AGE, meta);
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      return state.getValue(AGE);
   }

   @Override
   public BlockState createBlockState() {
      return new BlockState(this, AGE, NORTH, EAST, SOUTH, WEST, UPPER, FLIP, ALT);
   }
}
