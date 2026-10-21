package astolfo.via.gui;

import astolfo.via.ViaFabric;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class ProtocolButton extends GuiButton {
   private float sliderValue;
   public boolean dragging;
   public static float lastSliderValue;

   public ProtocolButton(int i, int j, int k) {
      super(i, j, k, 100, 20, "");
      this.sliderValue = lastSliderValue;
      super.displayString = "Protocol: " + ViaFabric.INSTANCE.getVersion().getName();
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
            this.applySliderValue(this.sliderValue);
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
         this.applySliderValue(this.sliderValue);
         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   private void applySliderValue(float f) {
      ProtocolVersion protocolversion = GuiMultiplayer.supportedProtocolVersions.get(this.getProtocolIndex((double)f));
      ViaFabric.INSTANCE.setVersion(protocolversion);
      lastSliderValue = f;
      super.displayString = "Protocol: " + ViaFabric.INSTANCE.getVersion().getName();
   }

   @Override
   public void mouseReleased(int mouseX, int mouseY) {
      this.dragging = false;
   }

   public int getProtocolIndex(double d) {
      if (d == 0.0) {
         ViaFabric.enabled = false;
         return 0;
      } else {
         int i = 0;

         for (double d0 = 0.0; d0 <= d; d0 += 0.033) {
            i++;
         }

         ViaFabric.enabled = true;
         return i;
      }
   }
}
