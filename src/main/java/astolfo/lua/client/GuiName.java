package astolfo.lua.client;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.gui.clickgui.defaultgui.DefaultClickGuiScreen;
import astolfo.gui.clickgui.transparent.TransparentClickGuiScreen;
import astolfo.module.ModuleManager;
import astolfo.module.exploits.HideWindow;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class GuiName extends ZeroArgFunction {
   @Override
   public LuaValue call() {
      GuiScreen guiscreen = ClientAccess.mc.currentScreen;
      if (guiscreen != null) {
         boolean flag = ClientAccess.mc.currentScreen instanceof GuiContainer;
         if (flag && !(ClientAccess.mc.currentScreen instanceof GuiInventory)) {
            return valueOf("chest");
         } else {
            boolean flag1 = ClientAccess.mc.currentScreen instanceof GuiInventory;
            if (flag1) {
               return valueOf("inventory");
            } else {
               boolean flag2 = ClientAccess.mc.currentScreen instanceof GuiChat;
               if (flag2) {
                  return valueOf("chat");
               } else {
                  boolean flag3 = ClientAccess.mc.currentScreen instanceof TransparentClickGuiScreen;
                  return !flag3 && !(ClientAccess.mc.currentScreen instanceof DefaultClickGuiScreen) ? valueOf("unknown") : valueOf("astolfo");
               }
            }
         }
      } else {
         Astolfo astolfo = ClientAccess.client;
         ModuleManager modulemanager1 = astolfo.getModuleManager();
         Class<HideWindow> oclass = HideWindow.class;
         ModuleManager modulemanager = modulemanager1;
         return modulemanager.get(oclass).isEnabled()
               && HideWindow.hiddenChest != null
               && HideWindow.hiddenWindowTitle != null
               && HideWindow.hiddenWindowTitle.getFormattedText() != null
            ? valueOf(HideWindow.hiddenWindowTitle.getFormattedText())
            : valueOf("none");
      }
   }
}
