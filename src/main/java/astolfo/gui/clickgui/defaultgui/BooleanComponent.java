package astolfo.gui.clickgui.defaultgui;

import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.component.Container;
import astolfo.module.Category;
import astolfo.module.visuals.HUD;
import astolfo.render.RenderUtil;
import astolfo.setting.BooleanSetting;
import astolfo.setting.Setting;
import net.minecraft.client.gui.GuiMainMenu;

public class BooleanComponent extends SettingComponent {
   private final Category category;
   private final Container ownerContainer;
   private static final String d = "Category-Colors";

   public BooleanComponent(Category category, Container container, Setting setting, int i, int j) {
      super(container, setting, i, j);
      this.category = category;
      this.ownerContainer = container;
   }

   @Override
   public void onClick(int i) {
      boolean flag = this.ownerContainer.isExpanded();
      if (flag && i == 0) {
         boolean flag1 = this.getBooleanSetting().getValue();
         BooleanSetting booleansetting = this.getBooleanSetting();
         booleansetting.set(!flag1);
      }
   }

   @Override
   public void render(int i, int j) {
      int i1 = HUD.gui.getSetting(d).asBoolean() ? this.category.getColor() : client.getColorSetting().getValue().getRGB();
      if (this.getBooleanSetting().getValue()) {
         RenderUtil.drawRect(
            (double)this.getX(), (double)this.getYPos(), (double)(this.getX() + (float)this.getWidth()), (double)(this.getYPos() + (float)this.getHeight()), i1
         );
      }

      GuiMainMenu.latoBoldFont15.drawString(this.getBooleanSetting().getName(), this.getX() + 3.0F, this.getYPos(), -1);
   }

   private BooleanSetting getBooleanSetting() {
      return (BooleanSetting)this.getSetting();
   }
}
