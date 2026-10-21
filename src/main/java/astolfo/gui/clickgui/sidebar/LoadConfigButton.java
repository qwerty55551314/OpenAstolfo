package astolfo.gui.clickgui.sidebar;

import astolfo.Astolfo;
import astolfo.config.ConfigFiles;
import astolfo.gui.component.Button;
import astolfo.notification.NotificationType;
import java.io.File;

public class LoadConfigButton extends Button {
   public LoadConfigButton(float f, float g, int i, int j) {
      super("Load config", f, g, i, j);
   }

   @Override
   public void onPress() {
      int b0 = 118;
      String s = SidebarGui.config.getValue();
      File file1 = new File(Astolfo.INSTANCE.getDirectory().getAbsolutePath(), s + ".json");
      if (!file1.exists()) {
         Astolfo astolfo = client;
         astolfo.getNotificationManager()
            .createNotification("Config manager", String.format("%s config doesnt exist", s), NotificationType.DANGER, 2500)
            .show();
      } else {
         ConfigFiles.save(ConfigFiles.serializeModules(), client.getConfigName());
         ConfigFiles.load(s);
         Astolfo astolfo1 = client;
         astolfo1.setConfigName(s);
         Astolfo astolfo2 = client;
         astolfo2.getNotificationManager().createNotification("Config manager", String.format("Loaded %s config", s), NotificationType.SUCCESS, 2500).show();
      }
   }
}
