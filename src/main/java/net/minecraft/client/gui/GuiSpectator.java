package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiSpectator extends Gui implements ISpectatorMenuRecipient {
   private static final ResourceLocation field_175267_f = new ResourceLocation("textures/gui/widgets.png");
   public static final ResourceLocation field_175269_a = new ResourceLocation("textures/gui/spectator_widgets.png");
   private final Minecraft field_175268_g;
   private long field_175270_h;
   private SpectatorMenu field_175271_i;

   public GuiSpectator(Minecraft mcIn) {
      this.field_175268_g = mcIn;
   }

   public void func_175260_a(int i) {
      this.field_175270_h = Minecraft.getSystemTime();
      if (this.field_175271_i != null) {
         this.field_175271_i.func_178644_b(i);
      } else {
         this.field_175271_i = new SpectatorMenu(this);
      }
   }

   private float func_175265_c() {
      long i = this.field_175270_h - Minecraft.getSystemTime() + 5000L;
      return MathHelper.clamp_float((float)i / 2000.0F, 0.0F, 1.0F);
   }

   public void renderTooltip(ScaledResolution scaledResolution, float f) {
      if (this.field_175271_i != null) {
         float f1 = this.func_175265_c();
         float f3;
         int j = (f3 = f1 - 0.0F) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
         if (j <= 0) {
            this.field_175271_i.func_178641_d();
         } else {
            int i = scaledResolution.getScaledWidth() / 2;
            float f1x = super.zLevel;
            super.zLevel = -90.0F;
            float f2 = (float)scaledResolution.getScaledHeight() - 22.0F * f1;
            SpectatorDetails spectatordetails = this.field_175271_i.func_178646_f();
            this.func_175258_a(scaledResolution, f1, i, f2, spectatordetails);
            super.zLevel = f1x;
         }
      }
   }

   public void func_175258_a(ScaledResolution scaledResolution, float f, int i, float g, SpectatorDetails spectatorDetails) {
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, f);
      this.field_175268_g.getTextureManager().bindTexture(field_175267_f);
      this.drawTexturedModalRect((float)(i - 91), g, 0, 0, 182, 22);
      if (spectatorDetails.func_178681_b() >= 0) {
         this.drawTexturedModalRect((float)(i - 91 - 1 + spectatorDetails.func_178681_b() * 20), g - 1.0F, 0, 22, 24, 22);
      }

      RenderHelper.enableGUIStandardItemLighting();

      for (int i1 = 0; i1 < 9; i1++) {
         this.func_175266_a(i1, scaledResolution.getScaledWidth() / 2 - 90 + i1 * 20 + 2, g + 3.0F, f, spectatorDetails.func_178680_a(i1));
      }

      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
   }

   private void func_175266_a(int i, int j, float f, float g, ISpectatorMenuObject iSpectatorMenuObject) {
      this.field_175268_g.getTextureManager().bindTexture(field_175269_a);
      if (iSpectatorMenuObject != SpectatorMenu.field_178657_a) {
         int i1 = (int)(g * 255.0F);
         GlStateManager.pushMatrix();
         float f1 = (float)j;
         GlStateManager.translate(f1, f, 0.0F);
         float f1x = iSpectatorMenuObject.func_178662_A_() ? 1.0F : 0.25F;
         GlStateManager.color(f1x, f1x, f1x, g);
         iSpectatorMenuObject.func_178663_a(f1x, i1);
         GlStateManager.popMatrix();
         String s = String.valueOf(GameSettings.getKeyDisplayString(this.field_175268_g.gameSettings.keyBindsHotbar[i].getKeyCode()));
         if (i1 > 3) {
            boolean flag = iSpectatorMenuObject.func_178662_A_();
            if (flag) {
               this.field_175268_g
                  .fontRendererObj
                  .drawStringWithShadow(s, (float)(j + 19 - 2 - this.field_175268_g.fontRendererObj.getStringWidth(s)), f + 6.0F + 3.0F, 16777215 + (i1 << 24));
            }
         }
      }
   }

   public void renderSelectedItem(ScaledResolution scaledResolution) {
      int i = (int)(this.func_175265_c() * 255.0F);
      if (i > 3) {
         SpectatorMenu spectatormenu = this.field_175271_i;
         if (spectatormenu != null) {
            ISpectatorMenuObject ispectatormenuobject = this.field_175271_i.func_178645_b();
            String s = ispectatormenuobject != SpectatorMenu.field_178657_a
               ? ispectatormenuobject.getSpectatorName().getFormattedText()
               : this.field_175271_i.func_178650_c().func_178670_b().getFormattedText();
            if (s != null) {
               int j = (scaledResolution.getScaledWidth() - this.field_175268_g.fontRendererObj.getStringWidth(s)) / 2;
               int k = scaledResolution.getScaledHeight() - 35;
               GlStateManager.pushMatrix();
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               this.field_175268_g.fontRendererObj.drawStringWithShadow(s, (float)j, (float)k, 16777215 + (i << 24));
               GlStateManager.disableBlend();
               GlStateManager.popMatrix();
            }
         }
      }
   }

   @Override
   public void func_175257_a(SpectatorMenu spectatorMenu) {
      this.field_175271_i = null;
      this.field_175270_h = 0L;
   }

   public boolean func_175262_a() {
      return this.field_175271_i != null;
   }

   public void func_175259_b(int i) {
      int i1 = this.field_175271_i.func_178648_e() + i;

      while (
         i1 >= 0
            && i1 <= 8
            && (this.field_175271_i.func_178643_a(i1) == SpectatorMenu.field_178657_a || !this.field_175271_i.func_178643_a(i1).func_178662_A_())
      ) {
         i1 += i;
      }

      if (i1 >= 0 && i1 <= 8) {
         this.field_175271_i.func_178644_b(i1);
         this.field_175270_h = Minecraft.getSystemTime();
      }
   }

   public void func_175261_b() {
      this.field_175270_h = Minecraft.getSystemTime();
      if (this.func_175262_a()) {
         int i = this.field_175271_i.func_178648_e();
         if (i != -1) {
            this.field_175271_i.func_178644_b(i);
         }
      } else {
         this.field_175271_i = new SpectatorMenu(this);
      }
   }
}
