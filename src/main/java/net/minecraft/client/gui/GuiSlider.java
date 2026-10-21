package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSlider extends GuiButton {
   private float sliderPosition = 1.0F;
   public boolean isMouseDown;
   private String name;
   private final float min;
   private final float max;
   private final GuiPageButtonList.GuiResponder responder;
   private GuiSlider.FormatHelper formatHelper;
   private static final String d = ": ";

   public GuiSlider(
      GuiPageButtonList.GuiResponder guiResponder,
      int idIn,
      int x,
      int y,
      String name,
      float minIn,
      float maxIn,
      float defaultValue,
      GuiSlider.FormatHelper formatter
   ) {
      super(idIn, x, y, 150, 20, "");
      this.name = name;
      this.min = minIn;
      this.max = maxIn;
      this.sliderPosition = (defaultValue - minIn) / (maxIn - minIn);
      this.formatHelper = formatter;
      this.responder = guiResponder;
      super.displayString = this.getDisplayString();
   }

   public float func_175220_c() {
      return this.min + (this.max - this.min) * this.sliderPosition;
   }

   public void func_175218_a(float f, boolean bl) {
      this.sliderPosition = (f - this.min) / (this.max - this.min);
      super.displayString = this.getDisplayString();
      if (bl) {
         this.responder.onTick(super.id, this.func_175220_c());
      }
   }

   public float func_175217_d() {
      return this.sliderPosition;
   }

   private String getDisplayString() {
      return this.formatHelper == null
         ? I18n.format(this.name) + d + this.func_175220_c()
         : this.formatHelper.getText(super.id, I18n.format(this.name), this.func_175220_c());
   }

   @Override
   public int getHoverState(boolean mouseOver) {
      return 0;
   }

   @Override
   public void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
      boolean flag = super.visible;
      if (flag) {
         if (this.isMouseDown) {
            this.sliderPosition = (float)(mouseX - (super.xPosition + 4)) / (float)(super.width - 8);
            float f;
            int i = (f = this.sliderPosition - 0.0F) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
            if (i < 0) {
               this.sliderPosition = 0.0F;
            }

            if (this.sliderPosition > 1.0F) {
               this.sliderPosition = 1.0F;
            }

            super.displayString = this.getDisplayString();
            this.responder.onTick(super.id, this.func_175220_c());
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderPosition * (float)(super.width - 8)), super.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderPosition * (float)(super.width - 8)) + 4, super.yPosition, 196, 66, 4, 20);
      }
   }

   public void func_175219_a(float f) {
      this.sliderPosition = f;
      super.displayString = this.getDisplayString();
      this.responder.onTick(super.id, this.func_175220_c());
   }

   @Override
   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
      if (super.mousePressed(mc, mouseX, mouseY)) {
         this.sliderPosition = (float)(mouseX - (super.xPosition + 4)) / (float)(super.width - 8);
         if (this.sliderPosition < 0.0F) {
            this.sliderPosition = 0.0F;
         }

         if (this.sliderPosition > 1.0F) {
            this.sliderPosition = 1.0F;
         }

         super.displayString = this.getDisplayString();
         this.responder.onTick(super.id, this.func_175220_c());
         this.isMouseDown = true;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void mouseReleased(int mouseX, int mouseY) {
      this.isMouseDown = false;
   }

   public interface FormatHelper {
      String getText(int var1, String var2, float var3);
   }
}
