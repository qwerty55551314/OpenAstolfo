package astolfo.gui.clickgui.transparent;

import astolfo.Astolfo;
import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.component.Container;
import astolfo.module.visuals.HUD;
import astolfo.render.RenderUtil;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import java.awt.Color;
import net.minecraft.util.EnumChatFormatting;

public class BooleanComponent extends SettingComponent {
   private static final Color grayColor = new Color(57, 57, 57);
   public static final Color darkColor = new Color(22, 22, 22);
   private int hoverAlpha;

   public BooleanComponent(Container container, Setting setting, int i, int j) {
      super(container, setting, i, j);
   }

   @Override
   public void render(int i, int j) {
      boolean flag = this.isHovered(i, j);
      if (flag) {
         if (this.hoverAlpha < 120) {
            this.hoverAlpha += 5;
         }
      } else {
         int i1 = this.hoverAlpha;
         if (i1 > 0) {
            this.hoverAlpha -= 5;
         }
      }

      int j1 = this.hoverAlpha;
      if (j1 > 0) {
         RenderUtil.drawRect(
            (double)(this.getX() + 1.5F),
            (double)(this.getYPos() - 1.0F),
            (double)(this.getX() + (float)this.getWidth() + 0.5F),
            (double)(this.getYPos() + (float)this.getHeight() + 4.0F),
            new Color(255, 255, 255, this.hoverAlpha).getRGB()
         );
      }

      RenderUtil.drawRect(
         (double)(this.getX() + 1.5F),
         (double)(this.getYPos() - 1.0F),
         (double)(this.getX() + (float)this.getWidth() + 0.5F),
         (double)(this.getYPos() + (float)this.getHeight() + 4.0F),
         new Color(1, 1, 1, 120).getRGB()
      );
      RenderUtil.drawRectOutline(
         (double)(this.getX() + 3.0F),
         (double)(this.getYPos() + 2.0F),
         (double)(this.getX() + 12.0F),
         (double)(this.getYPos() + 11.0F),
         new Color(137, 137, 137).getRGB()
      );
      boolean flag1 = this.getBooleanSetting().getValue();
      if (flag1) {
         float f = this.getX() + 6.0F;
         float f1 = this.getYPos() + 7.0F;
         float f2 = this.getX() + 8.0F;
         float f3 = this.getYPos() + 10.0F;
         Astolfo astolfo = client;
         RenderUtil.drawLine(2.0F, f, f1, f2, f3, astolfo.getColor().getRGB());
         f = this.getX() + 11.0F;
         f1 = this.getYPos() + 3.0F;
         f2 = this.getX() + 8.0F;
         f3 = this.getYPos() + 10.0F;
         Astolfo astolfo1 = client;
         RenderUtil.drawLine(2.0F, f, f1, f2, f3, astolfo1.getColor().getRGB());
      }

      client.getCustomFontRenderer()
         .drawStringWithShadow(
            EnumChatFormatting.GRAY + this.getBooleanSetting().getName().replace("-", " "), this.getX() + 15.0F, this.getYPos() + 0.5F, Color.WHITE.getRGB()
         );
      boolean flag2 = this.isHovered(i, j);
      if (flag2 && HUD.toolTips.getValue()) {
         client.getCustomFontRenderer().drawStringWithShadow(this.getBooleanSetting().getDescription(), (float)(i + 6), (float)(j - 4), -1);
      }
   }

   @Override
   public void onClick(int i) {
      boolean flag = this.getParentContainer().isExpanded();
      if (flag && i == 0) {
         boolean flag1 = this.getBooleanSetting().getValue();
         BooleanSetting booleansetting = this.getBooleanSetting();
         booleansetting.set(!flag1);
      }
   }

   private BooleanSetting getBooleanSetting() {
      return (BooleanSetting)this.getSetting();
   }
}
