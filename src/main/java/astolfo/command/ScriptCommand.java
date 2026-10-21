package astolfo.command;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.auth.DisconnectHandler;
import astolfo.script.Script;
import astolfo.script.ScriptManager;
import java.util.Arrays;
import net.minecraft.util.EnumChatFormatting;

public final class ScriptCommand extends Command {
   public static boolean scriptsChanged = true;
   private boolean unsafeWarningShown;

   public ScriptCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Arrays.asList("sc", "lua", "scripts"));
   }

   @Override
   public void runCommand(String[] strings) {
      boolean flag = DisconnectHandler.disconnected;
      if (flag) {
         ClientAccess.toasts.danger("You must reconnect to Astolfo before changing scripts");
      } else {
         int i = strings.length;
         switch (i) {
            case 2:
               boolean flag1 = strings[1].equals("reload");
               if (flag1) {
                  ClientAccess.toasts.infoMessage("Script manager", "Reloaded all scripts");
                  scriptsChanged = true;
                  Astolfo astolfo = ClientAccess.client;
                  astolfo.getScriptManager().reload();
                  return;
               }

               boolean flag2 = strings[1].equals("list");
               if (flag2) {
                  Astolfo astolfo1 = ClientAccess.client;

                  for (Script script3 : astolfo1.getScriptManager().getList()) {
                     ClientAccess.chat.createBuilder(false, false).append(".").append(script3.name).color(EnumChatFormatting.GRAY).build().send();
                  }

                  return;
               }

               boolean flag3 = strings[1].equals("unsafe");
               if (flag3) {
                  boolean flag4 = this.unsafeWarningShown;
                  if (!flag4 && !ScriptManager.unsafeFunctions.isEnabled()) {
                     ClientAccess.chat.printWarning("Enabling unsafe will allow Astolfo scripts to contact websites and load code from them");
                     ClientAccess.chat.print("Execute this command again to enable/toggle it");
                     this.unsafeWarningShown = true;
                  } else {
                     Script script4 = ScriptManager.unsafeFunctions;
                     boolean flag5 = ScriptManager.unsafeFunctions.isEnabled();
                     Script script2 = script4;
                     script2.setEnabled(!flag5);
                     ClientAccess.chat.printWarning("Unsafe functions: " + ScriptManager.unsafeFunctions.isEnabled());
                  }
               }
               break;
            case 3:
               Astolfo astolfo2 = ClientAccess.client;
               Script script = astolfo2.getScriptManager().getScript(strings[2]);
               boolean flag6 = strings[1].equals("enable");
               if (flag6 && script != null) {
                  if (!script.loaded && !script.loadAndRun()) {
                     return;
                  }

                  if (script.enable()) {
                     ScriptManager.savedStates.put(script.name, true);
                     scriptsChanged = true;
                     ClientAccess.toasts
                        .infoMessage(
                           "Script manager", String.format("Script %s%s has been enabled", EnumChatFormatting.GRAY, script.name + EnumChatFormatting.RESET)
                        );
                  } else {
                     ClientAccess.toasts.dangerMessage("Script manager", "This script is already enabled");
                  }
               }

               boolean flag7 = strings[1].equals("reload");
               if (flag7) {
                  if (script != null) {
                     script.disable();
                     Astolfo astolfo3 = ClientAccess.client;
                     ScriptManager scriptmanager = astolfo3.getScriptManager();
                     Script[] ascript = new Script[1];
                     Astolfo astolfo4 = ClientAccess.client;
                     ascript[0] = astolfo4.getScriptManager().getScript(script.name);
                     scriptmanager.remove(ascript);
                     Script script1 = new Script(script.path, script.name);
                     Astolfo astolfo5 = ClientAccess.client;
                     astolfo5.getScriptManager().add(new Script[]{script1});
                     scriptsChanged = true;
                     if (script.isEnabled()) {
                        script1.enable();
                     }
                  } else {
                     ClientAccess.toasts.dangerMessage("Script manager", "Script not found");
                  }
               }

               if (strings[1].equals("disable") && script != null) {
                  boolean flag8 = script.loaded;
                  if (flag8) {
                     boolean flag9 = script.disable();
                     if (flag9) {
                        ScriptManager.savedStates.put(script.name, false);
                        ClientAccess.toasts
                           .infoMessage(
                              "Script manager", String.format("Script %s%s has been disabled", EnumChatFormatting.GRAY, script.name + EnumChatFormatting.RESET)
                           );
                        scriptsChanged = true;
                     } else {
                        ClientAccess.toasts.dangerMessage("Script manager", "This script is already disabled");
                     }
                  } else {
                     ClientAccess.toasts.dangerMessage("Script manager", "This script is already disabled");
                  }
               }
         }
      }
   }
}
