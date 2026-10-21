package astolfo.gui.clickgui.defaultgui;

import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.clickgui.SettingContainer;
import astolfo.gui.component.Component;
import astolfo.gui.component.Container;
import astolfo.gui.component.Element;
import astolfo.module.Module;
import astolfo.module.visuals.HUD;
import astolfo.render.RenderUtil;
import astolfo.render.font.CustomFontRenderer;
import java.awt.Color;
import net.minecraft.client.gui.GuiMainMenu;
import org.lwjgl.input.Keyboard;

public class ModuleButton extends Container {
   private final CategoryPanel categoryPanel;
   private final Module module;
   private int openTicks = 0;
   private boolean listeningForBind;
   private static String H;

   public ModuleButton(CategoryPanel categoryPanel, Module module, int i, int j) {
      super(module.getName(), 0.0F, 0.0F, i, j);
      this.categoryPanel = categoryPanel;
      this.module = module;
   }

   @Override
   public void onClick(int i) {
      boolean flag = this.categoryPanel.isExpanded();
      if (flag) {
         if (i == 0) {
            this.module.setEnabled(!this.module.isEnabled());
         }

         if (i == 1 && DefaultClickGuiScreen.showKeybinds && Keyboard.isKeyDown(42)) {
            this.listeningForBind = !this.listeningForBind;
            return;
         }

         if (i == 1) {
            int i1 = this.openTicks;
            if (i1 > 0 && !this.getChildren().isEmpty()) {
               boolean flag1 = this.isExpanded();
               this.setExpanded(!flag1);
               if (this.isExpanded()) {
                  super.expandAnimation.backwards().rewind().start();
               } else {
                  super.expandAnimation.forwards().rewind().start();
               }
            }
         }
      }
   }

   @Override
   public void onKeyTyped(char c, int i) {
      if (DefaultClickGuiScreen.showKeybinds) {
         if (this.listeningForBind && i == 1) {
            this.listeningForBind = false;
         }

         if (this.listeningForBind) {
            this.module.setKey(i);
            this.listeningForBind = false;
         }
      }

      super.onKeyTyped(c, i);
   }

   @Override
   public void render(int i, int j) {
      int i1 = this.getExpandedHeight();
      boolean flag = HUD.gui.getSetting("Left-Align").asBoolean();
      boolean flag1 = HUD.gui.getSetting("Category-Colors").asBoolean();
      int j1 = flag1 ? this.categoryPanel.getCategory().getColor() : client.getColorSetting().getValue().getRGB();
      boolean flag2 = this.module.isEnabled();
      if (flag2) {
         if (this.isHovered(i, j)) {
            RenderUtil.drawRect(
               (double)this.getX(),
               (double)this.getYPos(),
               (double)(this.getX() + (float)this.getWidth()),
               (double)(this.getYPos() + (float)this.getHeight()),
               new Color(j1).darker().getRGB()
            );
         } else {
            RenderUtil.drawRect(
               (double)this.getX(),
               (double)this.getYPos(),
               (double)(this.getX() + (float)this.getWidth()),
               (double)(this.getYPos() + (float)this.getHeight()),
               j1
            );
         }
      } else {
         boolean flag3 = this.isHovered(i, j - 1);
         if (flag3) {
            RenderUtil.drawRect(
               (double)this.getX(),
               (double)this.getYPos(),
               (double)(this.getX() + (float)this.getWidth()),
               (double)(this.getYPos() + (float)this.getHeight()),
               new Color(52, 52, 52).getRGB()
            );
         }
      }

      boolean flag4 = this.isExpanded();
      if (flag4) {
         RenderUtil.drawRect(
            (double)this.getX(),
            (double)this.getYPos(),
            (double)(this.getX() + (float)this.getWidth()),
            (double)(this.getYPos() + (float)i1),
            new Color(37, 37, 37).darker().getRGB()
         );
      }

      boolean flag5 = DefaultClickGuiScreen.showKeybinds;
      if (flag5) {
         int k = this.module.getKey();
         if (k != 0 || this.listeningForBind) {
            try {
               String s5 = "[%s]";
               Object[] aobject2 = new Object[1];
               boolean flag6 = this.listeningForBind;
               Object[] aobject1 = aobject2;
               Object[] aobject = aobject2;
               aobject1[0] = flag6 ? "..." : Keyboard.getKeyName(this.module.getKey());
               String s = String.format("[%s]", aobject);
               CustomFontRenderer customfontrenderer = GuiMainMenu.latoBoldFont15;
               CustomFontRenderer customfontrenderer3;
               String s3;
               float f4;
               if (flag) {
                  customfontrenderer3 = customfontrenderer;
                  s3 = s;
                  f4 = this.getX() + (float)this.getWidth() - (float)GuiMainMenu.latoBoldFont15.getStringWidth(s) - 2.0F;
               } else {
                  customfontrenderer3 = customfontrenderer;
                  s3 = s;
                  f4 = this.getX() + 3.0F;
               }

               customfontrenderer3.drawString(s3, f4, this.getYPos() + 2.0F, Color.GRAY.getRGB());
            } catch (Exception exception) {
            }
         }
      }

      CustomFontRenderer customfontrenderer4 = GuiMainMenu.latoBoldFont15;
      String s1 = this.module.getName().toLowerCase();
      CustomFontRenderer customfontrenderer1 = customfontrenderer4;
      String s4;
      float f2;
      if (flag) {
         customfontrenderer4 = customfontrenderer1;
         s4 = s1;
         f2 = this.getX() + 3.0F;
      } else {
         customfontrenderer4 = customfontrenderer1;
         s4 = s1;
         f2 = this.getX() + (float)this.getWidth() - (float)GuiMainMenu.latoBoldFont15.getStringWidth(this.module.getName().toLowerCase(), true) - 3.0F;
      }

      float f3;
      int i1x;
      label95: {
         f3 = this.getYPos() + 3.0F;
         boolean flag7 = this.isExpanded();
         float f1 = f3;
         float f = f2;
         String s2 = s4;
         CustomFontRenderer customfontrenderer2 = customfontrenderer4;
         customfontrenderer4 = customfontrenderer2;
         s4 = s2;
         f2 = f;
         f3 = f1;
         if (flag7) {
            boolean flag8 = this.module.isEnabled();
            customfontrenderer4 = customfontrenderer2;
            s4 = s2;
            f2 = f;
            f3 = f1;
            if (flag8) {
               i1x = j1;
               break label95;
            }
         }

         i1x = 13421772;
      }

      customfontrenderer4.drawString(s4, f2, f3, i1x);
      boolean flag9 = this.isExpanded();
      if (flag9) {
         int l = 0;

         for (Element element : this.getChildren()) {
            if (element instanceof Component) {
               ((Component)element).setPosition(this.getX() + 2.0F, this.getYPos() + (float)l + 15.0F);
               if (this.isExpanded()) {
                  element.render(i, j);
               }

               l += ((Component)element).getHeight()
                  + (element instanceof MapComponent && ((MapComponent)element).isExpanded() ? ((MapComponent)element).getChildrenHeight() : 0);
            }
         }
      }

      if (this.categoryPanel.isExpanded()) {
         this.openTicks++;
      } else {
         this.openTicks = 0;
      }
   }

   public int getSettingsHeight() {
      int i = 0;
      if (this.isExpanded()) {
         for (Element element : this.getChildren()) {
            if (element instanceof SettingComponent || element instanceof SettingContainer) {
               i += ((Component)element).getHeight();
            }
         }
      }

      return i + 18;
   }

   public int getExpandedHeight() {
      int i = 0;
      if (this.isExpanded()) {
         for (Element element : this.getChildren()) {
            if (element instanceof SettingComponent || element instanceof SettingContainer) {
               i += ((Component)element).getHeight();
            }

            if (element instanceof MapComponent) {
               i += ((MapComponent)element).getChildrenHeight();
            }
         }
      }

      return i + 18;
   }

   public CategoryPanel getParent() {
      return this.categoryPanel;
   }
}
