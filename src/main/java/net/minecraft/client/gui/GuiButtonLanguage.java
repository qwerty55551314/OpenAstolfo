package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonLanguage extends GuiButton {
   public GuiButtonLanguage(int buttonID, int xPos, int yPos) {
      super(buttonID, xPos, yPos, 20, 20, "");
   }

   @Override
   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      boolean flag1 = super.visible;
      if (flag1) {
         boolean flag2;
         label22: {
            mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int j = super.xPosition;
            if (mouseX >= j) {
               int k = super.yPosition;
               if (mouseY >= k) {
                  int l = super.xPosition + super.width;
                  if (mouseX < l && mouseY < super.yPosition + super.height) {
                     flag2 = true;
                     break label22;
                  }
               }
            }

            flag2 = false;
         }

         boolean flag = flag2;
         int i = 106;
         if (flag) {
            i += super.height;
         }

         this.drawTexturedModalRect(super.xPosition, super.yPosition, 0, i, super.width, super.height);
      }
   }
}
