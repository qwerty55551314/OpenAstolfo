package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;

public class GuiConfirmOpenLink extends GuiYesNo {
   private final String openLinkWarning;
   private final String copyLinkButtonText;
   private final String linkText;
   private boolean showSecurityWarning = true;

   public GuiConfirmOpenLink(GuiYesNoCallback guiYesNoCallback, String linkTextIn, int i, boolean bl) {
      super(guiYesNoCallback, I18n.format(bl ? "chat.link.confirmTrusted" : "chat.link.confirm"), linkTextIn, i);
      super.confirmButtonText = I18n.format(bl ? "chat.link.open" : "gui.yes");
      super.cancelButtonText = I18n.format(bl ? "gui.cancel" : "gui.no");
      this.copyLinkButtonText = I18n.format("chat.copy");
      this.openLinkWarning = I18n.format("chat.link.warning");
      this.linkText = linkTextIn;
   }

   @Override
   public void initGui() {
      super.initGui();
      super.buttonList.clear();
      super.buttonList.add(new GuiButton(0, super.width / 2 - 50 - 105, super.height / 6 + 96, 100, 20, super.confirmButtonText));
      super.buttonList.add(new GuiButton(2, super.width / 2 - 50, super.height / 6 + 96, 100, 20, this.copyLinkButtonText));
      super.buttonList.add(new GuiButton(1, super.width / 2 - 50 + 105, super.height / 6 + 96, 100, 20, super.cancelButtonText));
   }

   @Override
   public void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 2) {
         this.copyLinkToClipboard();
      }

      super.parentScreen.confirmClicked(button.id == 0, super.parentButtonClickedId);
   }

   public void copyLinkToClipboard() {
      setClipboardString(this.linkText);
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(mouseX, mouseY, partialTicks);
      if (this.showSecurityWarning) {
         this.drawCenteredString(super.fontRendererObj, this.openLinkWarning, super.width / 2, 110, 16764108);
      }
   }

   public void disableSecurityWarning() {
      this.showSecurityWarning = false;
   }
}
