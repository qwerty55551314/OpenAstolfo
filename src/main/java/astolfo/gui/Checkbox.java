package astolfo.gui;

import astolfo.Astolfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Checkbox extends Gui {
   public String label;
   public int posX;
   public int posY;
   private boolean checked;

   public Checkbox(String string, int i, int j, boolean bl) {
      this.label = string;
      this.posX = i;
      this.posY = j;
      this.checked = bl;
   }

   public void handleMouseClick(int i, int j, int k) {
      if (this.isHovered(i, j)) {
         boolean flag = this.checked;
         this.checked = !flag;
      }
   }

   public void drawCheckbox(int i, int j) {
      boolean flag = this.isHovered(i, j);
      if (flag) {
         drawRect(this.posX, this.posY, this.posX + 15, this.posY + 15, -6250336);
         drawRect(this.posX + 1, this.posY + 1, this.posX + 14, this.posY + 14, -16777216);
         float f2 = (float)(this.posX + 18);
         float f3 = (float)(this.posY + 4);
         Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.label, f2, f3, 16777215);
      } else {
         drawRect(this.posX, this.posY, this.posX + 15, this.posY + 15, -8947849);
         drawRect(this.posX + 1, this.posY + 1, this.posX + 14, this.posY + 14, -16777216);
         float f = (float)(this.posX + 18);
         float f1 = (float)(this.posY + 4);
         Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.label, f, f1, 13421772);
      }

      boolean flag1 = this.checked;
      if (flag1) {
         drawRect(this.posX + 2, this.posY + 2, this.posX + 13, this.posY + 13, Astolfo.INSTANCE.getColorSetting().getValue().getRGB());
      }
   }

   public boolean isHovered(int i, int j) {
      int i1 = this.posX;
      if (i >= i1) {
         int j1 = this.posX + 20 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.label);
         if (i <= j1) {
            int k = this.posY;
            if (j >= k && j <= this.posY + 15) {
               return true;
            }
         }
      }

      return false;
   }

   public void setChecked(boolean bl) {
      this.checked = bl;
   }

   public boolean isChecked() {
      return this.checked;
   }
}
