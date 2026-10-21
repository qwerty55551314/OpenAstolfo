package astolfo.module.player;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import astolfo.util.GameUtil;
import astolfo.util.MathUtil;
import net.minecraft.util.EnumChatFormatting;

public final class Timer extends Module {
   private final NumberSetting<Float> speed = new NumberSetting<>("Speed", "Speed of timer", 1.0F, 0.1F, 10.0F);
   private final BooleanSetting tick = new BooleanSetting("Tick", "Dynamic timer", false);
   private final BooleanSetting onlyMoving = new BooleanSetting("Only-Moving", "Only when moving", true);
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;

   public Timer(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.tick, this.speed, this.onlyMoving});
   }

   @Override
   public void onDisable() {
      ClientAccess.mc.timer.timerSpeed = 1.0F;
      super.onDisable();
      ClientAccess.mc.timer.timerSpeed = 1.0F;
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName(this.getName() + " " + EnumChatFormatting.GRAY + MathUtil.ONE_DECIMAL.format(this.speed.getValue()));
      super.updateDisplayName();
   }

   private void onUpdate(UpdateEvent updateEvent) {
      this.updateDisplayName();
      boolean flag = this.onlyMoving.getValue();
      if (flag && !GameUtil.isMoving(ClientAccess.mc.thePlayer)) {
         ClientAccess.mc.timer.timerSpeed = 1.0F;
      } else {
         boolean flag1 = this.tick.getValue();
         if (flag1) {
            if (ClientAccess.mc.thePlayer.ticksExisted % 2 == 0) {
               ClientAccess.mc.timer.timerSpeed = this.speed.getValue();
            } else {
               ClientAccess.mc.timer.timerSpeed = 1.0F;
            }
         } else {
            ClientAccess.mc.timer.timerSpeed = this.speed.getValue();
         }
      }
   }
}
