package astolfo.module.combat;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.player.ReachEvent;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import astolfo.util.MathUtil;
import astolfo.util.RandomUtils;
import net.minecraft.util.EnumChatFormatting;

public final class Reach extends Module {
   private final NumberSetting<Double> minBlocks = new NumberSetting<>("Min-Blocks", "Randomizes reach on hit", 3.25, 3.0, 6.0);
   private final NumberSetting<Double> maxBlocks = new NumberSetting<>("Max-Blocks", "Randomizes reach on hit", 3.65, 3.0, 6.0);
   public double currentReach;
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;
   @EventHandler(ReachEvent.class)
   private Listener<ReachEvent> onReach = reachEvent -> reachEvent.setReach(this.currentReach);

   public Reach(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.minBlocks, this.maxBlocks});
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName(
         this.getName()
            + " "
            + EnumChatFormatting.GRAY
            + MathUtil.ONE_DECIMAL.format(this.minBlocks.getValue())
            + "-"
            + MathUtil.ONE_DECIMAL.format(this.maxBlocks.getValue())
      );
      super.updateDisplayName();
   }

   private void onUpdate(UpdateEvent updateEvent) {
      this.updateDisplayName();
      if (ClientAccess.mc.thePlayer.swingProgressInt == 3) {
         this.currentReach = RandomUtils.nextDouble(this.minBlocks.asDouble(), this.maxBlocks.asDouble());
      }
   }
}
