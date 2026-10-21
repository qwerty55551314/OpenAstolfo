package net.minecraft.src;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;

public class GuiScreenOF extends GuiScreen {
   public void actionPerformedRightClick(GuiButton button) throws IOException {
   }

   @Override
   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (mouseButton == 1) {
         GuiButton guibutton = getSelectedButton(super.buttonList, mouseX, mouseY);
         if (guibutton != null && guibutton.enabled) {
            guibutton.playPressSound(super.mc.getSoundHandler());
            this.actionPerformedRightClick(guibutton);
         }
      }
   }

   public static GuiButton getSelectedButton(List<GuiButton> list, int i, int j) {
      for (int i1 = 0; i1 < list.size(); i1++) {
         GuiButton guibutton = (GuiButton)list.get(i1);
         if (guibutton.visible) {
            int j1 = GuiVideoSettings.getButtonWidth(guibutton);
            int k = GuiVideoSettings.getButtonHeight(guibutton);
            if (i >= guibutton.xPosition && j >= guibutton.yPosition && i < guibutton.xPosition + j1 && j < guibutton.yPosition + k) {
               return guibutton;
            }
         }
      }

      return null;
   }
}
