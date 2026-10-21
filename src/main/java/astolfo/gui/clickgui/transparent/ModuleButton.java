package astolfo.gui.clickgui.transparent;

import astolfo.Astolfo;
import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.clickgui.SettingContainer;
import astolfo.gui.component.Component;
import astolfo.gui.component.Container;
import astolfo.gui.component.Element;
import astolfo.module.Module;
import astolfo.module.ModuleManager;
import astolfo.notification.NotificationType;
import astolfo.render.RenderUtil;
import astolfo.render.font.CustomFontRenderer;
import java.awt.Color;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ModuleButton extends Container {
   private Container parentPanel;
   private Module module;
   private int openTicks = 0;
   private final Color grayColor = new Color(85, 85, 85);
   private static final Color shadowColor = new Color(1, 1, 1, 70);
   private static final Color whiteColor = new Color(255, 255, 255, 170);
   private boolean listeningForBind;
   private int hoverAlpha;

   public ModuleButton(Container container, Module module, int i, int j) {
      super(module.getName(), 0.0F, 0.0F, i, j);
      this.parentPanel = container;
      this.module = module;
   }

   @Override
   public void render(int i, int j) {
      boolean flag = this.isHovered(i, j);
      if (flag) {
         if (this.hoverAlpha < 120) {
            this.hoverAlpha += 5;
         }
      } else {
         int j1 = this.hoverAlpha;
         if (j1 > 0) {
            this.hoverAlpha -= 5;
         }
      }

      int k = this.hoverAlpha;
      if (k > 0) {
         RenderUtil.drawRect(
            (double)(this.getX() + 1.0F),
            (double)(this.getYPos() - 1.0F),
            (double)(this.getX() + (float)this.getWidth() + 1.0F),
            (double)(this.getYPos() + (float)this.getHeight() + 4.0F),
            new Color(255, 255, 255, this.hoverAlpha).getRGB()
         );
      }

      if (this.listeningForBind) {
         boolean flag1 = Mouse.isButtonDown(0);
         if (flag1 && !this.isHovered(i, j)) {
            this.listeningForBind = false;
         }
      }

      RenderUtil.drawRect(
         (double)(this.getX() + 1.0F),
         (double)(this.getYPos() - 1.0F),
         (double)(this.getX() + (float)this.getWidth() + 1.0F),
         (double)(this.getYPos() + (float)this.getHeight() + 4.0F),
         new Color(1, 1, 1, 120).getRGB()
      );
      CustomFontRenderer customfontrenderer1 = client.getCustomFontRenderer();
      String s1 = this.module.getName();
      float f4 = this.getX() + 3.0F;
      boolean flag2 = this.listeningForBind;
      float f1 = this.getYPos();
      float f = f4;
      String s = s1;
      CustomFontRenderer customfontrenderer = customfontrenderer1;
      customfontrenderer1 = customfontrenderer;
      s1 = s;
      f4 = f;
      float f5 = f1;
      int i1;
      if (flag2) {
         i1 = Color.RED.getRGB();
      } else {
         boolean flag3 = this.module.isEnabled();
         customfontrenderer1 = customfontrenderer;
         s1 = s;
         f4 = f;
         f5 = f1;
         if (flag3) {
            Astolfo astolfo = client;
            i1 = astolfo.getColor().getRGB();
         } else {
            i1 = new Color(154, 154, 154).getRGB();
         }
      }

      customfontrenderer1.drawStringWithShadow(s1, f4, f5, i1);
      boolean flag4 = this.getChildren().isEmpty();
      if (!flag4) {
         if (!this.isExpanded()) {
            float f2 = this.getX() + (float)this.getWidth() - 10.0F;
            f4 = this.getYPos() + 4.0F;
            int j1x;
            if (this.module.isEnabled()) {
               Astolfo astolfo1 = client;
               j1x = astolfo1.getColor().getRGB();
            } else {
               j1x = new Color(154, 154, 154).getRGB();
            }

            this.drawChevron(f2, f4, 7.0F, 4.0F, 2.0F, j1x);
         } else {
            float f3 = this.getX() + (float)this.getWidth() - 10.0F;
            f4 = this.getYPos() + 8.0F;
            int l;
            if (this.module.isEnabled()) {
               Astolfo astolfo2 = client;
               l = astolfo2.getColor().getRGB();
            } else {
               l = new Color(154, 154, 154).getRGB();
            }

            this.drawChevron(f3, f4, 7.0F, -4.0F, 2.0F, l);
         }
      }

      boolean flag5 = this.isExpanded();
      if (flag5 || super.expandAnimation.isRunning()) {
         int i1x = 0;

         for (Element element : this.getChildren()) {
            boolean flag6 = element instanceof Component;
            if (flag6) {
               ((Component)element).setPosition(this.getX() + 1.0F, this.getYPos() + (float)i1x + 15.0F);
               if (this.isExpanded()) {
                  element.render(i, j);
               }

               i1x += 15 + (element instanceof MapComponent && ((MapComponent)element).isExpanded() ? ((MapComponent)element).getChildrenHeight() : 0);
               i1x = (int)((double)i1x - super.expandAnimation.getValue(0.0, (double)i1x));
            }
         }
      }

      if (this.module.isEnabled()) {
         super.stateAnimation.backwards().start();
      } else {
         super.stateAnimation.forwards().start();
      }

      if (this.parentPanel.isExpanded()) {
         this.openTicks++;
      } else {
         this.openTicks = 0;
      }
   }

   @Override
   public boolean isHovered(int i, int j) {
      float f;
      int i1 = (f = (float)i - this.getX()) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
      if (i1 >= 0) {
         float f1;
         int j1 = (f1 = (float)i - (this.getX() + (float)this.getWidth())) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
         if (j1 <= 0) {
            float f2;
            int k = (f2 = (float)j - this.getYPos()) == 0.0F ? 0 : (f2 < 0.0F ? -1 : 1);
            if (k >= 0) {
               float f3;
               int l = (f3 = (float)j - (this.getYPos() + (float)this.getHeight())) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
               if (l <= 0) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private void drawChevron(float f, float g, float h, float f2, float f3, int i) {
      RenderUtil.drawLine(f3, f, g, f + h / 2.0F, g + f2, i);
      RenderUtil.drawLine(f3, f + h / 2.0F, g + f2, f + h, g, i);
   }

   private int t() {
      int i = 0;
      if (this.isExpanded()) {
         for (Element element : this.getChildren()) {
            if (element instanceof Component) {
               i += 15;
            }

            if (element instanceof MapComponent) {
               i += ((MapComponent)element).getChildrenHeight() + 1;
            }
         }
      }

      return i;
   }

   @Override
   public void onClick(int i) {
      boolean flag = this.parentPanel.isExpanded();
      if (flag) {
         if (i == 0) {
            if (this.listeningForBind) {
               this.listeningForBind = false;
               return;
            }

            Astolfo astolfo = client;
            ModuleManager modulemanager1 = astolfo.getModuleManager();
            String s = this.module.getName();
            ModuleManager modulemanager = modulemanager1;
            Module modulex = modulemanager.getModule(s);
            modulex.setEnabled(!modulex.isEnabled());
         }

         if (i == 1 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54))) {
            this.listeningForBind = true;
            return;
         }

         if (i == 1) {
            int i1 = this.openTicks;
            if (i1 > 0) {
               boolean flag1 = this.getChildren().isEmpty();
               if (!flag1 && !this.listeningForBind) {
                  boolean flag2 = this.isExpanded();
                  this.setExpanded(!flag2);
                  if (this.isExpanded()) {
                     super.expandAnimation.backwards().rewind().start();
                  } else {
                     super.expandAnimation.forwards().rewind().start();
                  }
               }
            }
         }
      }
   }

   @Override
   public void onKeyTyped(char c, int i) {
      if (this.listeningForBind && ChatAllowedCharacters.isAllowedCharacter(c)) {
         Astolfo astolfo = client;
         astolfo.getNotificationManager()
            .createNotification(String.format("%s's bind is now %s", this.getModule().getName(), Character.toUpperCase(c)), "", NotificationType.SUCCESS, 1500)
            .show();
         this.getModule().setKey(i);
         this.listeningForBind = false;
      }

      super.onKeyTyped(c, i);
   }

   public int getExpandedHeight() {
      int b0 = 0;
      if (this.isExpanded()) {
         for (Element element : this.getChildren()) {
            if (element instanceof SettingComponent || element instanceof SettingContainer) {
               b0 += 15;
            }
         }
      }

      return b0 + 15;
   }

   public Module getModule() {
      return this.module;
   }
}
