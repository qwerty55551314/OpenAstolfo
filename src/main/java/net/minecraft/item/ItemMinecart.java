package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemMinecart extends Item {
   private static final IBehaviorDispenseItem dispenserMinecartBehavior = new BehaviorDefaultDispenseItem() {
      private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
      private static final long c = -1707401812585217048L;

      @Override
      public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
         EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
         World world = source.getWorld();
         double d0 = source.getX() + (double)enumfacing.getFrontOffsetX() * 1.125;
         double d1 = Math.floor(source.getY()) + (double)enumfacing.getFrontOffsetY();
         String[] astring = new String[0];
         double d2 = source.getZ() + (double)enumfacing.getFrontOffsetZ() * 1.125;
         BlockPos blockpos = source.getBlockPos().offset(enumfacing);
         IBlockState iblockstate = world.getBlockState(blockpos);
         BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.getBlock() instanceof BlockRailBase
            ? iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty())
            : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         double d3;
         if (BlockRailBase.isRailBlock(iblockstate)) {
            if (blockrailbase$enumraildirection.isAscending()) {
               d3 = 0.6;
            } else {
               d3 = 0.1;
            }
         } else {
            if (iblockstate.getBlock().getMaterial() != Material.air) {
               return this.behaviourDefaultDispenseItem.dispense(source, stack);
            }

            IBlockState iblockstate2 = world.getBlockState(blockpos.down());
            if (!BlockRailBase.isRailBlock(iblockstate2)) {
               return this.behaviourDefaultDispenseItem.dispense(source, stack);
            }

            IBlockState iblockstate1 = world.getBlockState(blockpos.down());
            BlockRailBase.EnumRailDirection blockrailbase$enumraildirection1 = iblockstate1.getBlock() instanceof BlockRailBase
               ? iblockstate1.getValue(((BlockRailBase)iblockstate1.getBlock()).getShapeProperty())
               : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            if (enumfacing != EnumFacing.DOWN && blockrailbase$enumraildirection1.isAscending()) {
               d3 = -0.4;
            } else {
               d3 = -0.9;
            }
         }

         EntityMinecart entityminecart = EntityMinecart.getMinecart(world, d0, d1 + d3, d2, ((ItemMinecart)stack.getItem()).minecartType);
         boolean flag = stack.hasDisplayName();
         if (flag) {
            entityminecart.setCustomNameTag(stack.getDisplayName());
         }

         world.spawnEntityInWorld(entityminecart);
         stack.splitStack(1);
         return stack;
      }

      @Override
      public void playDispenseSound(IBlockSource source) {
         source.getWorld().playAuxSFX((int)c, source.getBlockPos(), 0);
      }
   };
   private final EntityMinecart.EnumMinecartType minecartType;

   public ItemMinecart(EntityMinecart.EnumMinecartType typeIn) {
      super.maxStackSize = 1;
      this.minecartType = typeIn;
      this.setCreativeTab(CreativeTabs.tabTransport);
      BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
   }

   @Override
   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
      String[] astring = new String[0];
      IBlockState iblockstate = worldIn.getBlockState(pos);
      if (BlockRailBase.isRailBlock(iblockstate)) {
         if (!worldIn.isRemote) {
            BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.getBlock() instanceof BlockRailBase
               ? iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty())
               : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            double d0 = 0.0;
            if (blockrailbase$enumraildirection.isAscending()) {
               d0 = 0.5;
            }

            EntityMinecart entityminecart = EntityMinecart.getMinecart(
               worldIn, (double)pos.getX() + 0.5, (double)pos.getY() + 0.0625 + d0, (double)pos.getZ() + 0.5, this.minecartType
            );
            if (stack.hasDisplayName()) {
               entityminecart.setCustomNameTag(stack.getDisplayName());
            }

            worldIn.spawnEntityInWorld(entityminecart);
         }

         stack.stackSize--;
         return true;
      } else {
         return false;
      }
   }
}
