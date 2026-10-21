package astolfo.module.combat;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.MapSetting;
import astolfo.setting.MapSettingBuilder;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import astolfo.util.GameUtil;
import astolfo.util.RandomUtils;
import astolfo.util.Stopwatch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public final class AutoClicker extends Module {
   private final NumberSetting<Integer> minCPS = new NumberSetting<>("Min-CPS", "Minimal CPS", 8, 1, 20);
   private final NumberSetting<Integer> maxCPS = new NumberSetting<>("Max-CPS", "Maximum CPS", 12, 1, 20);
   private final BooleanSetting blockCheck = new BooleanSetting("Block-Check", "Stops autoclicking if you aim at a block", true);
   private final BooleanSetting swordCheck = new BooleanSetting("Sword-Check", "Stops autoclicking if you dont have sword on your hand", false);
   private final MapSetting block = new MapSettingBuilder("Block")
      .ordered()
      .description("push velocity")
      .add("Enable", new BooleanSetting("Enable", "Enable block hitting", true))
      .add("Chance", new NumberSetting<>("Chance", "Push back amount", 0, 1, 100))
      .build();
   private int clickDelay;
   private final Stopwatch clickTimer = new Stopwatch();
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;

   public AutoClicker(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.block, this.minCPS, this.maxCPS, this.blockCheck, this.swordCheck});
   }

   @Override
   public void updateDisplayName() {
      this.setDisplayName("Auto Clicker " + EnumChatFormatting.GRAY + this.minCPS.getValue() + "-" + this.maxCPS.getValue());
      super.updateDisplayName();
   }

   private void onUpdate(UpdateEvent updateEvent) {
      this.updateDisplayName();
      int i = this.maxCPS.getValue();
      if (i < this.minCPS.getValue()) {
         this.maxCPS.setValue(this.minCPS.getValue());
      }

      boolean flag1 = this.swordCheck.getValue();
      if (!flag1 || GameUtil.isHoldingSword(ClientAccess.mc.thePlayer)) {
         if (!ClientAccess.mc.isGamePaused()) {
            Minecraft minecraft = ClientAccess.mc;
            if (minecraft.currentScreen == null) {
               boolean flag2 = ClientAccess.mc.inGameHasFocus;
               if (flag2) {
                  boolean flag3 = Mouse.isButtonDown(0);
                  if (flag3) {
                     boolean flag4 = this.blockCheck.getValue();
                     if (flag4
                        && ClientAccess.mc.objectMouseOver != null
                        && ClientAccess.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                        && !ClientAccess.mc.thePlayer.capabilities.isCreativeMode) {
                        KeyBinding.onTick(ClientAccess.mc.gameSettings.keyBindAttack.getKeyCode());
                        ClientAccess.mc.gameSettings.keyBindAttack.pressed = true;
                        return;
                     }

                     boolean flag5 = this.clickTimer.hasElapsed((float)this.clickDelay);
                     if (flag5) {
                        this.clickTimer.reset();
                        KeyBinding.onTick(ClientAccess.mc.gameSettings.keyBindAttack.getKeyCode());
                        ClientAccess.mc.gameSettings.keyBindAttack.pressed = false;
                        boolean flag6 = this.block.getSetting("Enable").asBoolean();
                        if (flag6 && GameUtil.isHoldingSword(ClientAccess.mc.thePlayer) && Mouse.isButtonDown(1)) {
                           KeyBinding.onTick(ClientAccess.mc.gameSettings.keyBindUseItem.getKeyCode());
                           ClientAccess.mc.gameSettings.keyBindUseItem.pressed = true;
                           float f = (float)(100 - this.block.getSetting("Chance").asInt());
                           boolean flag = (double)RandomUtils.RANDOM.nextFloat() > (f != 0.0F ? (double)(f / 100.0F) : -0.1);
                           if (flag) {
                              KeyBinding.onTick(ClientAccess.mc.gameSettings.keyBindUseItem.getKeyCode());
                              ClientAccess.mc.gameSettings.keyBindUseItem.pressed = false;
                           }
                        }

                        double d2 = (double)this.minCPS.getValue().intValue();
                        double d0 = (double)(this.maxCPS.getValue() - this.minCPS.getValue());
                        double d1 = 1.0 / (d2 + Math.random() * d0);
                        this.clickDelay = (int)Math.floor(d1 * 1000.0);
                     }
                  }
               }
            }
         }
      }
   }
}
