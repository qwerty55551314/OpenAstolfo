package astolfo.gui.tabgui;

import astolfo.gui.TabGui;

public interface TabGuiEntry {
   String getDisplayText();

   default void onPress() {
   }

   default void onExpand() {
   }

   void handleKeyPress(int var1);

   TabGui.Panel getPanel();
}
