package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

public class GuiOptionSlider extends GuiButton {
   private float sliderValue = 1.0F;
   public boolean dragging;
   private GameSettings.Options options;
   private final float field_146132_r;
   private final float field_146131_s;

   public GuiOptionSlider(int i, int j, int k, GameSettings.Options options) {
      this(i, j, k, options, 0.0F, 1.0F);
   }

   public GuiOptionSlider(int i, int j, int k, GameSettings.Options options, float f, float g) {
      super(i, j, k, 150, 20, "");
      this.options = options;
      this.field_146132_r = f;
      this.field_146131_s = g;
      Minecraft minecraft = Minecraft.getMinecraft();
      this.sliderValue = options.normalizeValue(minecraft.gameSettings.getOptionFloatValue(options));
      super.displayString = minecraft.gameSettings.getKeyBinding(options);
   }

   @Override
   public int getHoverState(boolean mouseOver) {
      return 0;
   }

   @Override
   public void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
      if (super.visible) {
         if (this.dragging) {
            this.sliderValue = (float)(mouseX - (super.xPosition + 4)) / (float)(super.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
            float f = this.options.denormalizeValue(this.sliderValue);
            mc.gameSettings.setOptionFloatValue(this.options, f);
            this.sliderValue = this.options.normalizeValue(f);
            super.displayString = mc.gameSettings.getKeyBinding(this.options);
         }

         mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (float)(super.width - 8)), super.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (float)(super.width - 8)) + 4, super.yPosition, 196, 66, 4, 20);
      }
   }

   @Override
   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
      if (super.mousePressed(mc, mouseX, mouseY)) {
         this.sliderValue = (float)(mouseX - (super.xPosition + 4)) / (float)(super.width - 8);
         this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
         mc.gameSettings.setOptionFloatValue(this.options, this.options.denormalizeValue(this.sliderValue));
         super.displayString = mc.gameSettings.getKeyBinding(this.options);
         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void mouseReleased(int mouseX, int mouseY) {
      this.dragging = false;
   }
}
