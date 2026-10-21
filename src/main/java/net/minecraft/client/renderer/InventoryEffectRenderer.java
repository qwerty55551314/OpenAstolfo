package net.minecraft.client.renderer;

import java.util.Collection;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public abstract class InventoryEffectRenderer extends GuiContainer {
   private boolean hasActivePotionEffects;

   public InventoryEffectRenderer(Container inventorySlotsIn) {
      super(inventorySlotsIn);
   }

   @Override
   public void initGui() {
      super.initGui();
      this.updateActivePotionEffects();
   }

   public void updateActivePotionEffects() {
      super.guiLeft = (super.width - super.xSize) / 2;
      this.hasActivePotionEffects = !super.mc.thePlayer.getActivePotionEffects().isEmpty();
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(mouseX, mouseY, partialTicks);
      if (this.hasActivePotionEffects) {
         this.drawActivePotionEffects();
      }
   }

   private void drawActivePotionEffects() {
      int i = super.guiLeft - 124;
      int j = super.guiTop;
      Collection collection = super.mc.thePlayer.getActivePotionEffects();
      boolean flag = collection.isEmpty();
      if (!flag) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         int k = 33;
         if (collection.size() > 5) {
            k = 132 / (collection.size() - 1);
         }

         for (PotionEffect potioneffect : super.mc.thePlayer.getActivePotionEffects()) {
            Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            super.mc.getTextureManager().bindTexture(GuiContainer.inventoryBackground);
            this.drawTexturedModalRect(i, j, 0, 166, 140, 32);
            if (potion.hasStatusIcon()) {
               int l = potion.getStatusIconIndex();
               this.drawTexturedModalRect(i + 6, j + 7, 0 + l % 8 * 18, 198 + l / 8 * 18, 18, 18);
            }

            String s1 = I18n.format(potion.getName());
            int i1 = potioneffect.getAmplifier();
            if (i1 == 1) {
               s1 = s1 + " " + I18n.format("enchantment.level.2");
            } else {
               int j1 = potioneffect.getAmplifier();
               if (j1 == 2) {
                  s1 = s1 + " " + I18n.format("enchantment.level.3");
               } else if (potioneffect.getAmplifier() == 3) {
                  s1 = s1 + " " + I18n.format("enchantment.level.4");
               }
            }

            super.fontRendererObj.drawStringWithShadow(s1, (float)(i + 10 + 18), (float)(j + 6), 16777215);
            String s = Potion.getDurationString(potioneffect);
            super.fontRendererObj.drawStringWithShadow(s, (float)(i + 10 + 18), (float)(j + 6 + 10), 8355711);
            j += k;
         }
      }
   }
}
