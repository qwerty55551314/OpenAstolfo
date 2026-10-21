package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiLabel extends Gui {
   public int field_146167_a = 200;
   public int field_146161_f = 20;
   public int field_146162_g;
   public int field_146174_h;
   private List<String> field_146173_k;
   public int field_175204_i;
   private boolean centered;
   public boolean visible = true;
   private boolean labelBgEnabled;
   private int field_146168_n;
   private int field_146169_o;
   private int field_146166_p;
   private int field_146165_q;
   private FontRenderer fontRenderer;
   private int field_146163_s;

   public GuiLabel(FontRenderer fontRendererObj, int i, int j, int k, int l, int m, int n) {
      this.fontRenderer = fontRendererObj;
      this.field_175204_i = i;
      this.field_146162_g = j;
      this.field_146174_h = k;
      this.field_146167_a = l;
      this.field_146161_f = m;
      this.field_146173_k = Lists.newArrayList();
      this.centered = false;
      this.labelBgEnabled = false;
      this.field_146168_n = n;
      this.field_146169_o = -1;
      this.field_146166_p = -1;
      this.field_146165_q = -1;
      this.field_146163_s = 0;
   }

   public void func_175202_a(String string) {
      this.field_146173_k.add(I18n.format(string));
   }

   public GuiLabel setCentered() {
      this.centered = true;
      return this;
   }

   public void drawLabel(Minecraft mc, int mouseX, int mouseY) {
      boolean flag = this.visible;
      if (flag) {
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         this.drawLabelBackground(mc, mouseX, mouseY);
         int i = this.field_146174_h + this.field_146161_f / 2 + this.field_146163_s / 2;
         int j = i - this.field_146173_k.size() * 10 / 2;

         for (int k = 0; k < this.field_146173_k.size(); k++) {
            if (this.centered) {
               this.drawCenteredString(
                  this.fontRenderer, this.field_146173_k.get(k), this.field_146162_g + this.field_146167_a / 2, j + k * 10, this.field_146168_n
               );
            } else {
               this.drawString(this.fontRenderer, this.field_146173_k.get(k), this.field_146162_g, j + k * 10, this.field_146168_n);
            }
         }
      }
   }

   public void drawLabelBackground(Minecraft mcIn, int i, int j) {
      if (this.labelBgEnabled) {
         int i1 = this.field_146167_a + this.field_146163_s * 2;
         int j1 = this.field_146161_f + this.field_146163_s * 2;
         int k = this.field_146162_g - this.field_146163_s;
         int l = this.field_146174_h - this.field_146163_s;
         drawRect(k, l, k + i1, l + j1, this.field_146169_o);
         drawHorizontalLine(k, k + i1, l, this.field_146166_p);
         drawHorizontalLine(k, k + i1, l + j1, this.field_146165_q);
         drawVerticalLine(k, l, l + j1, this.field_146166_p);
         drawVerticalLine(k + i1, l, l + j1, this.field_146165_q);
      }
   }
}
