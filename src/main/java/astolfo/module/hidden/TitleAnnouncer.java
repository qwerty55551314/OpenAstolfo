package astolfo.module.hidden;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.render.RenderAfterScreenEvent;
import astolfo.module.HiddenModule;
import astolfo.util.Stopwatch;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public final class TitleAnnouncer extends HiddenModule {
   public static final Stopwatch titleTimer = new Stopwatch();
   public static String pendingTitle;
   @EventHandler(RenderAfterScreenEvent.class)
   private Listener<RenderAfterScreenEvent> onRenderAfterScreen = renderAfterScreenEvent -> {
      boolean flag = titleTimer.hasElapsed(5000.0F);
      if (!flag && !StringUtils.isEmpty(pendingTitle)) {
         GuiIngame guiingame = ClientAccess.mc.ingameGUI;
         if (guiingame != null) {
            ClientAccess.mc.ingameGUI.displayedSubTitle = "";
            ClientAccess.mc.ingameGUI.setDefaultTitlesTimes();
            ClientAccess.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), 1.2F));
            ClientAccess.mc.ingameGUI.displayTitle(pendingTitle, "", 500, 5000, 500);
            pendingTitle = null;
         }
      }
   };

   public TitleAnnouncer() {
      ClientAccess.client.getEventBus().subscribe(this);
   }
}
