package astolfo.notification;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.render.RenderAfterScreenEvent;
import astolfo.gui.clickgui.defaultgui.DefaultClickGuiScreen;
import astolfo.manager.Manager;
import astolfo.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class NotificationManager extends Manager<Notification> implements ClientAccess {
   @EventHandler(RenderAfterScreenEvent.class)
   private Listener<RenderAfterScreenEvent> onRenderAfterScreen = renderAfterScreenEvent -> {
      boolean flag = super.items.isEmpty();
      if (!flag) {
         int i = renderAfterScreenEvent.getHeight() - 40;

         for (int j = 0; j < super.items.size(); j++) {
            Notification notification = (Notification)super.items.get(j);
            if (notification != null && notification.timerAnimation != null) {
               double d0;
               int k1 = (d0 = notification.timerAnimation.getValue() - 1.0) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
               if (k1 >= 0) {
                  if (!notification.slideAnimation.isRunning()) {
                     notification.slideAnimation.backwards().start();
                  }

                  if (notification.slideAnimation.getValue() <= 0.0) {
                     super.items.remove(notification);
                  }
               } else {
                  boolean flag1 = notification.isTimerRunning();
                  if (!flag1) {
                     notification.start();
                  }
               }

               int k = Math.max(
                     ClientAccess.client.getCustomFontRenderer().getStringWidth(notification.getTitle()),
                     ClientAccess.client.getCustomFontRenderer().getStringWidth(notification.getMessage())
                  )
                  + 30;
               boolean flag2 = notification.getMessage().isEmpty();
               int l = flag2 ? 10 : 20;
               int i1 = (int)((double)renderAfterScreenEvent.getWidth() - notification.slideAnimation.getValue(0.0, (double)(k + 2)));
               boolean flag3 = ClientAccess.mc.currentScreen instanceof DefaultClickGuiScreen;
               if (flag3) {
                  i1 = (int)notification.slideAnimation.getValue(0.0, (double)k)
                     - Math.max(
                        ClientAccess.client.getCustomFontRenderer().getStringWidth(notification.getTitle()),
                        ClientAccess.client.getCustomFontRenderer().getStringWidth(notification.getMessage())
                     )
                     - 30;
               }

               GL11.glEnableClientState(32884);
               GL11.glDisable(3553);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               RenderUtil.drawRect((double)(i1 - 2), (double)(i - 4), (double)(i1 + k + 2), (double)(i + l + 4), 1879048192);
               double d1;
               int l1 = (d1 = notification.timerAnimation.getValue() - 1.0) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
               if (l1 <= 0) {
                  RenderUtil.drawRect(
                     (double)(i1 - 2),
                     (double)(i + l + 2),
                     (double)((float)((double)i1 + notification.timerAnimation.getValue((double)(k + 2), 0.0))),
                     (double)(i + l + 4),
                     notification.getType().color
                  );
               }

               RenderUtil.setColor(notification.getType().color);
               Minecraft.getMinecraft()
                  .getTextureManager()
                  .bindTexture(new ResourceLocation(String.format("client/contexts/%s.png", notification.getType().name())));
               boolean flag4 = notification.getMessage().isEmpty();
               int j1 = flag4 ? 8 : 16;
               Gui.drawModalRectWithCustomSizedTexture(i1 + 1, i + 1, 0.0F, 0.0F, j1, j1, (float)j1, (float)j1);
               ClientAccess.client.getCustomFontRenderer().drawStringWithShadow(notification.getTitle(), (float)(i1 + 20), (float)i - 1.5F, -3355444);
               boolean flag5 = notification.getMessage().isEmpty();
               if (!flag5) {
                  ClientAccess.client.getCustomFontRenderer().drawStringWithShadow(notification.getMessage(), (float)(i1 + 20), (float)(i + 9), -6710887);
               }

               GL11.glDisableClientState(32884);
               GL11.glEnable(3553);
               GL11.glDisable(3042);
               i = (int)((double)i - (notification.slideAnimation.getValue() * 12.0 + (double)l));
            } else {
               super.items.remove(notification);
            }
         }
      }
   };

   public NotificationManager() {
      Astolfo.INSTANCE.getEventBus().subscribe(this);
   }

   public Notification createNotification(String string, String string2, NotificationType notificationType, int i) {
      return new Notification(string, string2, notificationType, i);
   }
}
