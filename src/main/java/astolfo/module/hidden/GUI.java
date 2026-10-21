package astolfo.module.hidden;

import astolfo.ClientAccess;
import astolfo.auth.DisconnectHandler;
import astolfo.gui.clickgui.GuiMode;
import astolfo.gui.clickgui.defaultgui.DefaultClickGuiScreen;
import astolfo.gui.clickgui.sidebar.SidebarClickGuiScreen;
import astolfo.gui.clickgui.transparent.TransparentClickGuiScreen;
import astolfo.gui.screen.AuthReconnectScreen;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.module.visuals.HUD;

public final class GUI extends Module {
   public static SidebarClickGuiScreen sidebarGui = new SidebarClickGuiScreen();
   public static TransparentClickGuiScreen transparentGui = new TransparentClickGuiScreen();
   public static DefaultClickGuiScreen defaultGui = new DefaultClickGuiScreen();

   public GUI(Category category) {
      super(category);
      this.setKey(54);
   }

   @Override
   public void onEnable() {
      if (DisconnectHandler.disconnected) {
         ClientAccess.mc.displayGuiScreen(new AuthReconnectScreen());
      } else {
         openClickGui();
      }

      super.onEnable();
      this.setEnabled(false);
   }

   public static void openClickGui() {
      switch ((GuiMode)HUD.gui.getSetting("Mode").getValue()) {
         case DEFAULT:
            ClientAccess.mc.displayGuiScreen(defaultGui);
            break;
         case TRANSPARENT:
            ClientAccess.mc.displayGuiScreen(transparentGui);
      }
   }
}
