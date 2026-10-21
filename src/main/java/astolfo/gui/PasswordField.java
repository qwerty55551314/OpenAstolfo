package astolfo.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class PasswordField extends GuiTextField {
   private boolean passwordMode = false;
   private boolean revealed = false;

   public PasswordField(int i, int j, int k, int l, int m) {
      super(i, Minecraft.getMinecraft().fontRendererObj, j, k, l, m);
   }

   public void setPasswordMode(boolean bl) {
      this.passwordMode = bl;
   }

   public void setRevealed(boolean bl) {
      if (!this.passwordMode) {
         this.revealed = true;
      }

      this.revealed = bl;
   }

   public boolean isTextShown() {
      return !this.passwordMode ? true : this.revealed;
   }

   public boolean isPasswordMode() {
      return this.passwordMode;
   }

   @Override
   public void drawTextBox() {
      boolean flag3;
      label29: {
         boolean flag1 = this.passwordMode;
         if (flag1) {
            boolean flag2 = this.revealed;
            if (!flag2) {
               flag3 = true;
               break label29;
            }
         }

         flag3 = false;
      }

      boolean flag = flag3;
      String s = "";
      if (flag) {
         s = this.getText();
         String s1 = "";

         for (int i = 0; i < this.getText().length(); i++) {
            s1 = s1 + "*";
         }

         this.setText(s1);
      }

      super.drawTextBox();
      if (flag) {
         this.setText(s);
      }
   }
}
