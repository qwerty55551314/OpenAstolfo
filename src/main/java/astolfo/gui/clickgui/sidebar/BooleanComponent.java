package astolfo.gui.clickgui.sidebar;

import astolfo.gui.animation.Animation;
import astolfo.gui.animation.Easing;
import astolfo.gui.animation.TimedAnimation;
import astolfo.render.RenderUtil;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import java.awt.Color;

public class BooleanComponent extends SidebarSettingComponent {
   public Animation checkAnimation = new TimedAnimation().setDuration(150).setEasing(Easing.LINEAR);

   public BooleanComponent(ModuleButton moduleButton, Setting setting, float f, float g, int i, int j) {
      super(moduleButton, setting, f, g, i, j);
   }

   @Override
   public void onClick(int i) {
      if (i == 0 && this.isVisible()) {
         boolean flag = this.getBooleanSetting().getValue();
         BooleanSetting booleansetting = this.getBooleanSetting();
         booleansetting.set(!flag);
      }
   }

   @Override
   public void render(int i, int j) {
      boolean flag = this.checkAnimation.isRunning();
      if (!flag) {
         if (this.getBooleanSetting().getValue()) {
            this.checkAnimation.forwards().start();
         } else {
            this.checkAnimation.backwards().start();
         }
      }

      int i1 = (int)this.checkAnimation.getValue(0.0, 255.0);
      BooleanSetting booleansetting = this.getBooleanSetting();
      RenderUtil.drawRect(
         (double)(this.getX() + (float)this.getWidth() - 9.0F),
         (double)(this.getYPos() - 1.5F),
         (double)(this.getX() + (float)this.getWidth() + 0.5F),
         (double)(this.getYPos() + 8.5F),
         new Color(255, 255, 255, 120).getRGB()
      );
      RenderUtil.drawRect(
         (double)(this.getX() + (float)this.getWidth() - 8.5F),
         (double)(this.getYPos() - 1.0F),
         (double)(this.getX() + (float)this.getWidth()),
         (double)(this.getYPos() + 8.0F),
         new Color(29, 31, 37, super.fadeAlpha).getRGB()
      );
      RenderUtil.drawRect(
         (double)(this.getX() + (float)this.getWidth() - 7.5F),
         (double)this.getYPos(),
         (double)(this.getX() + (float)this.getWidth() - 1.0F),
         (double)(this.getYPos() + 7.0F),
         new Color(255, 255, 255, i1).getRGB()
      );
      float f = this.getX() + (float)this.getWidth() - 6.5F;
      RenderUtil.drawRect(
         (double)f,
         (double)(this.getYPos() + 1.0F),
         (double)(this.getX() + (float)this.getWidth() - 2.0F),
         (double)(this.getYPos() + 6.0F),
         new Color(218, 223, 225, i1).getRGB()
      );
      if (super.fadeAlpha > 40) {
         client.getCustomFontRenderer().drawString(booleansetting.getName(), this.getX(), this.getYPos(), new Color(255, 255, 255, super.fadeAlpha).getRGB());
      }
   }

   public BooleanSetting getBooleanSetting() {
      return (BooleanSetting)this.getSetting();
   }
}
