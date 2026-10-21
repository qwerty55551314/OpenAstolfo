package astolfo.gui.clickgui.sidebar;

import astolfo.Astolfo;
import astolfo.config.ConfigFiles;
import astolfo.gui.component.Button;
import astolfo.notification.NotificationType;

public class SaveConfigButton extends Button {
   private static int q;

   public SaveConfigButton(float f, float g, int i, int j) {
      super("Save config", f, g, i, j);
   }

   @Override
   public void onPress() {
      String s = SidebarGui.config.getValue();
      ConfigFiles.save(ConfigFiles.serializeModules(), s);
      Astolfo astolfo = client;
      astolfo.setConfigName(s);
      Astolfo astolfo1 = client;
      astolfo1.getNotificationManager().createNotification("Config manager", String.format("Saved %s config", s), NotificationType.SUCCESS, 2500).show();
   }

   public static int C() {
      return 118;
   }
}
