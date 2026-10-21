package astolfo.module.other;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public final class FastPlace extends Module {
   private final NumberSetting<Integer> delay = new NumberSetting<>("Delay", "Placing delay", 0, 0, 3, " ticks");
   private final BooleanSetting onlyBlocks = new BooleanSetting("Only-Blocks", "Only works with blocks", false);
   @EventHandler(UpdateEvent.class)
   private final Listener<UpdateEvent> onUpdate = this::onUpdate;

   public FastPlace(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.delay, this.onlyBlocks});
      this.setHidden(false);
   }

   @Override
   public void onDisable() {
      ClientAccess.mc.rightClickDelayTimer = 4;
      super.onDisable();
   }

   private void onUpdate(UpdateEvent updateEvent) {
      int k = ClientAccess.mc.rightClickDelayTimer;
      int j = this.delay.getValue();
      int i = k;
      if (i != j && ClientAccess.mc.rightClickDelayTimer > this.delay.getValue()) {
         if (this.onlyBlocks.getValue()) {
            ItemStack itemstack = ClientAccess.mc.thePlayer.getHeldItem();
            if (itemstack == null || !(ClientAccess.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
               return;
            }
         }

         ClientAccess.mc.rightClickDelayTimer = this.delay.getValue();
      }
   }
}
