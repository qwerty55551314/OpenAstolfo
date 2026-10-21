package astolfo.module.movement;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.player.MotionEvent;
import astolfo.event.player.MoveEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import astolfo.util.GameUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class Terrain extends Module {
   private final BooleanSetting fastIce = new BooleanSetting("Fast-Ice", "Goes fast on ice", true);
   private final BooleanSetting fastLadder = new BooleanSetting("Fast-Ladder", "Pushes you up when about to ladder", true);
   private static final Block[] iceBlocks = new Block[]{Blocks.ice, Blocks.packed_ice};
   @EventHandler(MotionEvent.class)
   private Listener<MotionEvent> onMotion = this::onMotion;
   @EventHandler(MoveEvent.class)
   private Listener<MoveEvent> onMove = this::onMove;

   public Terrain(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.fastIce, this.fastLadder});
      this.setHidden(false);
   }

   @Override
   public void onDisable() {
      this.setIceSlipperiness(0.98F);
      super.onDisable();
   }

   private void setIceSlipperiness(float f) {
      for (Block block : iceBlocks) {
         block.slipperiness = f;
      }
   }

   public static int getHeightAboveGround(EntityPlayer entityPlayer) {
      for (int i = MathHelper.floor_double(entityPlayer.posY); i > 0; i--) {
         BlockPos blockpos = new BlockPos(MathHelper.floor_double(entityPlayer.posX), i, MathHelper.floor_double(entityPlayer.posZ));
         if (isSolidGround(ClientAccess.mc.theWorld.getBlockState(blockpos).getBlock())) {
            return MathHelper.floor_double(entityPlayer.posY) - i - 1;
         }
      }

      return -1;
   }

   private static boolean isSolidGround(Block block) {
      if (block != Blocks.air && block != Blocks.tallgrass && block != Blocks.deadbush && block != Blocks.double_plant) {
         BlockStaticLiquid blockstaticliquid = Blocks.lava;
         if (block != blockstaticliquid) {
            BlockStaticLiquid blockstaticliquid1 = Blocks.water;
            if (block != blockstaticliquid1 && block != Blocks.ladder) {
               return true;
            }
         }
      }

      return false;
   }

   private void onMove(MoveEvent moveEvent) {
      Block block = GameUtil.getBlockAtPlayer(-0.1);
      if (!this.fastIce.getValue() || !(block instanceof BlockIce) && !(block instanceof BlockPackedIce)) {
         this.setIceSlipperiness(0.98F);
      } else {
         moveEvent.setX(moveEvent.getX() * 2.0);
         moveEvent.setZ(moveEvent.getZ() * 2.0);
         this.setIceSlipperiness(0.6F);
      }
   }

   private void onMotion(MotionEvent motionEvent) {
      EventState eventstate = motionEvent.getState();
      if (eventstate == EventState.PRE) {
         boolean flag = this.fastLadder.getValue();
         if (flag) {
            boolean flag1 = ClientAccess.mc.thePlayer.isOnLadder();
            if (flag1) {
               boolean flag2 = ClientAccess.mc.gameSettings.keyBindForward.pressed;
               if (flag2 && getHeightAboveGround(ClientAccess.mc.thePlayer) < 1) {
                  ClientAccess.mc.thePlayer.motionY = 0.5;
               }
            }
         }
      }
   }
}
