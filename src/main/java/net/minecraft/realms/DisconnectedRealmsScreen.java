package net.minecraft.realms;

import java.util.List;
import net.minecraft.util.IChatComponent;

public class DisconnectedRealmsScreen extends RealmsScreen {
   private String title;
   private IChatComponent reason;
   private List<String> lines;
   private final RealmsScreen parent;
   private int textHeight;
   private static final String d = "gui.back";

   public DisconnectedRealmsScreen(RealmsScreen parentIn, String unlocalizedTitle, IChatComponent reasonIn) {
      this.parent = parentIn;
      this.title = getLocalizedString(unlocalizedTitle);
      this.reason = reasonIn;
   }

   @Override
   public void init() {
      Realms.setConnectedToRealms(false);
      this.buttonsClear();
      this.lines = this.fontSplit(this.reason.getFormattedText(), this.width() - 50);
      this.textHeight = this.lines.size() * this.fontLineHeight();
      this.buttonsAdd(newButton(0, this.width() / 2 - 100, this.height() / 2 + this.textHeight / 2 + this.fontLineHeight(), getLocalizedString(d)));
   }

   @Override
   public void keyPressed(char c, int i) {
      if (i == 1) {
         Realms.setScreen(this.parent);
      }
   }

   @Override
   public void buttonClicked(RealmsButton realmsButton) {
      if (realmsButton.id() == 0) {
         Realms.setScreen(this.parent);
      }
   }

   @Override
   public void render(int i, int j, float f) {
      this.renderBackground();
      this.drawCenteredString(this.title, this.width() / 2, this.height() / 2 - this.textHeight / 2 - this.fontLineHeight() * 2, 11184810);
      int i1 = this.height() / 2 - this.textHeight / 2;
      if (this.lines != null) {
         for (String s : this.lines) {
            this.drawCenteredString(s, this.width() / 2, i1, 16777215);
            i1 += this.fontLineHeight();
         }
      }

      super.render(i, j, f);
   }
}
