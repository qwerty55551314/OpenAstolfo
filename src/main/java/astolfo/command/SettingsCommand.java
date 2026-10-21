package astolfo.command;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.auth.DisconnectHandler;
import astolfo.config.ConfigFiles;
import astolfo.notification.NotificationType;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import net.minecraft.util.Util;
import org.lwjgl.Sys;

public final class SettingsCommand extends Command {
   public SettingsCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Arrays.asList("config", "s", "c"));
   }

   @Override
   public void runCommand(String[] strings) {
      boolean flag = DisconnectHandler.disconnected;
      if (flag) {
         ClientAccess.toasts.danger("You must reconnect to Astolfo before loading a config");
      } else {
         switch (strings.length) {
            case 2:
               String s2 = strings[1];
               int b1 = -1;
               int j = s2.hashCode();
               switch (j) {
                  case -1268966290:
                     if (s2.equals("folder")) {
                        b1 = 0;
                     }
                  default:
                     switch (b1) {
                        case 0:
                           openFolder(ClientAccess.client.getDirectory());
                           return;
                        default:
                           return;
                     }
               }
            case 3:
               String s = strings[2];
               String s1 = strings[1];
               int b0 = -1;
               int i = s1.hashCode();
               switch (i) {
                  case 3327206:
                     if (s1.equals("load")) {
                        b0 = 0;
                     }
                     break;
                  case 3522941:
                     if (s1.equals("save")) {
                        b0 = 1;
                     }
               }

               switch (b0) {
                  case 0:
                     File file1 = new File(Astolfo.INSTANCE.getDirectory().getAbsolutePath(), s + ".json");
                     if (!file1.exists()) {
                        Astolfo astolfo = ClientAccess.client;
                        astolfo.getNotificationManager()
                           .createNotification("Config manager", String.format("%s config doesnt exist", s), NotificationType.DANGER, 2500)
                           .show();
                        return;
                     }

                     ConfigFiles.load(s);
                     Astolfo astolfo1 = ClientAccess.client;
                     astolfo1.setConfigName(s);
                     Astolfo astolfo2 = ClientAccess.client;
                     astolfo2.getNotificationManager()
                        .createNotification("Config manager", String.format("Loaded %s config", s), NotificationType.SUCCESS, 2500)
                        .show();
                     break;
                  case 1:
                     ConfigFiles.save(ConfigFiles.serializeModules(), s);
                     Astolfo astolfo3 = ClientAccess.client;
                     astolfo3.setConfigName(s);
                     Astolfo astolfo4 = ClientAccess.client;
                     astolfo4.getNotificationManager()
                        .createNotification("Config manager", String.format("Saved %s config", s), NotificationType.SUCCESS, 2500)
                        .show();
               }
         }
      }
   }

   public static void openFolder(File file) {
      String s = file.getAbsolutePath();
      Util.EnumOS util$enumos2 = Util.getOSType();
      Util.EnumOS util$enumos1 = Util.EnumOS.OSX;
      Util.EnumOS util$enumos = util$enumos2;
      if (util$enumos == util$enumos1) {
         try {
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open", s});
            return;
         } catch (IOException ioexception1) {
         }
      } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
         String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", s);

         try {
            Runtime.getRuntime().exec(s1);
            return;
         } catch (IOException ioexception) {
         }
      }

      boolean flag = false;

      try {
         Class oclass = Class.forName("java.awt.Desktop");
         Object object = oclass.getMethod("getDesktop").invoke(null);
         oclass.getMethod("browse", URI.class).invoke(object, file.toURI());
      } catch (Throwable throwable) {
         flag = true;
      }

      if (flag) {
         Sys.openURL("file://" + s);
      }
   }
}
